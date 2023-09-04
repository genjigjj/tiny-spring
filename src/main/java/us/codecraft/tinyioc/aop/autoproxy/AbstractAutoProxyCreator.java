package us.codecraft.tinyioc.aop.autoproxy;

import org.aopalliance.aop.Advice;
import us.codecraft.tinyioc.aop.Advisor;
import us.codecraft.tinyioc.aop.Pointcut;
import us.codecraft.tinyioc.aop.ProxyFactory;
import us.codecraft.tinyioc.aop.TargetSource;
import us.codecraft.tinyioc.aop.adapter.AdvisorAdapterRegistry;
import us.codecraft.tinyioc.aop.adapter.DefaultAdvisorAdapterRegistry;
import us.codecraft.tinyioc.beans.BeansException;
import us.codecraft.tinyioc.beans.factory.BeanFactory;
import us.codecraft.tinyioc.beans.factory.BeanFactoryAware;
import us.codecraft.tinyioc.beans.factory.config.SmartInstantiationAwareBeanPostProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * AbstractAutoProxyCreator
 *
 * @author 郭佳俊
 * @since 2023/8/31
 **/
public abstract class AbstractAutoProxyCreator implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware {

    protected static final Object[] DO_NOT_PROXY = null;

    private final Map<Object, Object> earlyProxyReferences = new ConcurrentHashMap<>(16);

    private final Map<Object, Boolean> advisedBeans = new ConcurrentHashMap<>(256);

    private BeanFactory beanFactory;

    private AdvisorAdapterRegistry advisorAdapterRegistry = new DefaultAdvisorAdapterRegistry();


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean != null) {
            Object cacheKey = getCacheKey(bean.getClass(), beanName);
            if (this.earlyProxyReferences.remove(cacheKey) != bean) {
                return wrapIfNecessary(bean, beanName, cacheKey);
            }
        }
        return bean;
    }


    @Override
    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        Object cacheKey = getCacheKey(bean.getClass(), beanName);
        this.earlyProxyReferences.put(cacheKey, bean);
        return wrapIfNecessary(bean, beanName, cacheKey);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    protected Object wrapIfNecessary(Object bean, String beanName, Object cacheKey) {

        if (Boolean.FALSE.equals(this.advisedBeans.get(cacheKey))) {
            return bean;
        }
        // 基础设施类，跳过
        if (isInfrastructureClass(bean.getClass())) {
            this.advisedBeans.put(cacheKey, Boolean.FALSE);
            return bean;
        }
        // Create proxy if we have advice.
        Object[] specificInterceptors = getAdvicesAndAdvisorsForBean(bean.getClass(), beanName, null);
        if (specificInterceptors != null) {
            this.advisedBeans.put(cacheKey, Boolean.TRUE);
            Object proxy = createProxy(
                    bean.getClass(), beanName, specificInterceptors, new TargetSource(bean));
            return proxy;
        }

        this.advisedBeans.put(cacheKey, Boolean.FALSE);
        return bean;
    }

    protected Object createProxy(Class<?> beanClass, String beanName,
                                 Object[] specificInterceptors, TargetSource targetSource) {

        ProxyFactory proxyFactory = new ProxyFactory();

        Advisor[] advisors = buildAdvisors(beanName, specificInterceptors);
        proxyFactory.addAdvisors(advisors);
        proxyFactory.setTargetSource(targetSource);

        return proxyFactory.getProxy();
    }

    protected Advisor[] buildAdvisors(String beanName, Object[] specificInterceptors) {
        // Handle prototypes correctly...

        List<Object> allInterceptors = new ArrayList<>();
        if (specificInterceptors != null) {
            allInterceptors.addAll(Arrays.asList(specificInterceptors));
        }
        Advisor[] advisors = new Advisor[allInterceptors.size()];
        for (int i = 0; i < allInterceptors.size(); i++) {
            advisors[i] = this.advisorAdapterRegistry.wrap(allInterceptors.get(i));
        }
        return advisors;
    }

    protected boolean isInfrastructureClass(Class<?> beanClass) {
        boolean retVal = Advice.class.isAssignableFrom(beanClass) ||
                Pointcut.class.isAssignableFrom(beanClass) ||
                Advisor.class.isAssignableFrom(beanClass);
        return retVal;
    }


    protected Object getCacheKey(Class<?> beanClass, String beanName) {
        return beanClass;
    }

    protected abstract Object[] getAdvicesAndAdvisorsForBean(Class<?> beanClass, String beanName,
                                                             TargetSource customTargetSource) throws BeansException;
}
