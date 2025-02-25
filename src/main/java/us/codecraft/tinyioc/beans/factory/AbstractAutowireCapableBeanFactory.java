package us.codecraft.tinyioc.beans.factory;

import us.codecraft.tinyioc.BeanReference;
import us.codecraft.tinyioc.beans.*;
import us.codecraft.tinyioc.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;
import us.codecraft.tinyioc.beans.factory.support.AutowireCapableBeanFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 可自动装配内容的BeanFactory
 *
 * @author yihua.huang@dianping.com
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

    private boolean allowCircularReferences = true;

    @Override
    protected Object createBean(String beanName, BeanDefinition mbd, Object[] args) throws BeansException {
        Object beanInstance = doCreateBean(beanName, mbd, args);
        return beanInstance;
    }

    protected Object doCreateBean(String beanName, BeanDefinition mbd, Object[] args) throws BeansException {
        Object bean = instantiateBean(beanName, mbd, args);
        boolean earlySingletonExposure = (mbd.isSingleton() && this.allowCircularReferences &&
                isSingletonCurrentlyInCreation(beanName));
        if (earlySingletonExposure) {
            addSingletonFactory(beanName, () -> getEarlyBeanReference(beanName, mbd, bean));
        }
        Object exposedObject = bean;
        populateBean(beanName, mbd, exposedObject);
        exposedObject = initializeBean(exposedObject, beanName);
        if (earlySingletonExposure) {
            Object earlySingletonReference = getSingleton(beanName, false);
            if (earlySingletonReference != null) {
                if (exposedObject == bean) {
                    exposedObject = earlySingletonReference;
                }
            }
        }
        return exposedObject;
    }

    private Object getEarlyBeanReference(String beanName, BeanDefinition mbd, Object bean) {
        Object exposedObject = bean;
        if (!mbd.isSynthetic() && hasInstantiationAwareBeanPostProcessors()) {
            for (BeanPostProcessor bp : getBeanPostProcessors()) {
                if (bp instanceof SmartInstantiationAwareBeanPostProcessor) {
                    SmartInstantiationAwareBeanPostProcessor ibp = (SmartInstantiationAwareBeanPostProcessor) bp;
                    exposedObject = ibp.getEarlyBeanReference(exposedObject, beanName);
                }
            }
        }
        return exposedObject;
    }

    private Object instantiateBean(String beanName, BeanDefinition mbd, Object[] args) throws BeansException {
        try {
            return mbd.getBeanClass().newInstance();
        } catch (Exception e) {
            throw new BeansException("instantiateBean " + beanName + "error");
        }
    }

    private void populateBean(String beanName, BeanDefinition mbd, Object exposedObject) throws BeansException {
        PropertyValues pvs = mbd.getPropertyValues();
        applyPropertyValues(beanName, mbd, exposedObject, pvs);
    }

    public Object initializeBean(Object existingBean, String beanName) {
        return initializeBean(beanName, existingBean, null);
    }

    protected Object initializeBean(final String beanName, final Object bean, BeanDefinition mbd) {
        invokeAwareMethods(beanName, bean);
        Object wrappedBean = bean;
        if (mbd == null) {
            wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
        }
        invokeInitMethods(beanName, wrappedBean, mbd);
        if (mbd == null) {
            wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
        }

        return wrappedBean;
    }

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessBeforeInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) {
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    protected void invokeAwareMethods(String beanName, Object bean) {
        if (bean instanceof BeanNameAware) {
            ((BeanNameAware) bean).setBeanName(beanName);
        }
        ;
        if (bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(AbstractAutowireCapableBeanFactory.this);
        }

    }

    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition mbd) {
        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
    }


    protected void applyPropertyValues(String beanName, BeanDefinition mbd, Object bean, PropertyValues pvs) throws BeansException {
        for (PropertyValue propertyValue : pvs.getPropertyValues()) {
            Object value = propertyValue.getValue();
            if (value instanceof BeanReference) {
                BeanReference beanReference = (BeanReference) value;
                value = getBean(beanReference.getName());
            }
            try {
                Method declaredMethod = bean.getClass().getDeclaredMethod(
                        "set" + propertyValue.getName().substring(0, 1).toUpperCase()
                                + propertyValue.getName().substring(1), value.getClass());
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(bean, value);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                Field declaredField = null;
                try {
                    declaredField = bean.getClass().getDeclaredField(propertyValue.getName());
                    declaredField.setAccessible(true);
                    declaredField.set(bean, value);
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    throw new BeansException("applyPropertyValues error", ex);
                }

            }
        }
    }

    @Override
    protected Object postProcessObjectFromFactoryBean(Object object, String beanName) {
        return applyBeanPostProcessorsAfterInitialization(object, beanName);
    }

}
