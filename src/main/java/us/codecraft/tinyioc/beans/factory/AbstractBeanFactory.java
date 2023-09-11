package us.codecraft.tinyioc.beans.factory;

import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.BeansException;
import us.codecraft.tinyioc.beans.factory.config.ConfigurableBeanFactory;
import us.codecraft.tinyioc.beans.factory.config.InstantiationAwareBeanPostProcessor;
import us.codecraft.tinyioc.beans.factory.support.DefaultSingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yihua.huang@dianping.com
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

	private volatile boolean hasInstantiationAwareBeanPostProcessors;

	private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>(16);

	private List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

	@Override
	public <T> T getBean(String name,  Class<T> requiredType) throws BeansException {
		return doGetBean(name, requiredType, null);
	}

	@Override
	public Object getBean(String name) throws BeansException {
		return doGetBean(name, null, null);
	}

	protected <T> T doGetBean(final String name,  final Class<T> requiredType,
							   final Object[] args) throws BeansException {
		final String beanName = transformedBeanName(name);
		Object bean;
		Object sharedInstance = getSingleton(beanName);

		if (sharedInstance != null && args == null) {
			bean = sharedInstance;
		} else {
			BeanDefinition beanDefinition = getBeanDefinition(beanName);
			if (beanDefinition.isSingleton()) {
				sharedInstance = getSingleton(beanName, () -> createBean(beanName, beanDefinition, args));
				bean = getObjectForBeanInstance(sharedInstance, name, beanName, beanDefinition);
			} else if (beanDefinition.isPrototype()) {
				Object prototypeInstance = createBean(name, beanDefinition, args);
				bean = getObjectForBeanInstance(prototypeInstance, name, beanName, beanDefinition);
			} else {
				throw new BeansException("doGetBean error");
			}
		}
		return (T) bean;
	}

	protected Object getObjectForBeanInstance(Object beanInstance, String name, String beanName, BeanDefinition beanDefinition) {
		if (!(beanInstance instanceof FactoryBean) || name.startsWith(BeanFactory.FACTORY_BEAN_PREFIX)) {
			return beanInstance;
		}
		Object object = null;
		if (beanDefinition == null) {
			object = getCachedObjectForFactoryBean(beanName);
		}
		if (object == null) {
			// Return bean instance from factory.
			FactoryBean<?> factory = (FactoryBean<?>) beanInstance;
			object = getObjectFromFactoryBean(factory, beanName);
		}
		return object;
	}

	protected Object getCachedObjectForFactoryBean(String beanName) {
		return this.factoryBeanObjectCache.get(beanName);
	}

	protected Object getObjectFromFactoryBean(FactoryBean<?> factory, String beanName) {
		if (factory.isSingleton() && containsSingleton(beanName)) {
			Object object = this.factoryBeanObjectCache.get(beanName);
			if (object == null) {
				object = factory.getObject();
				// Only post-process and store if not put there already during getObject() call above
				// (e.g. because of circular reference processing triggered by custom getBean calls)
				Object alreadyThere = this.factoryBeanObjectCache.get(beanName);
				if (alreadyThere != null) {
					object = alreadyThere;
				} else {
					if (isSingletonCurrentlyInCreation(beanName)) {
						// Temporarily return non-post-processed object, not storing it yet..
						return object;
					}
					beforeSingletonCreation(beanName);
					try {
						object = postProcessObjectFromFactoryBean(object, beanName);
					} catch (Throwable ex) {
						throw new BeansException(
								beanName + "Post-processing of FactoryBean's singleton object failed", ex);
					} finally {
						afterSingletonCreation(beanName);
					}
					if (containsSingleton(beanName)) {
						this.factoryBeanObjectCache.put(beanName, object);
					}
				}
			}
			return object;
		} else {
			Object object = factory.getObject();
			object = postProcessObjectFromFactoryBean(object, beanName);
			return object;
		}
	}

	private String transformedBeanName(String name) {
		String beanName = name;
		while (beanName.startsWith(BeanFactory.FACTORY_BEAN_PREFIX)) {
			beanName = beanName.substring(BeanFactory.FACTORY_BEAN_PREFIX.length());
		}
		return beanName;
	}

	protected abstract Object createBean(String beanName, BeanDefinition mbd,  Object[] args) throws BeansException;


	public List<BeanPostProcessor> getBeanPostProcessors() {
		return this.beanPostProcessors;
	}

	protected Object postProcessObjectFromFactoryBean(Object object, String beanName) throws BeansException {
		return object;
	}

	protected abstract BeanDefinition getBeanDefinition(String beanName);


	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
		// Remove from old position, if any
		this.beanPostProcessors.remove(beanPostProcessor);
		if (beanPostProcessor instanceof InstantiationAwareBeanPostProcessor) {
			this.hasInstantiationAwareBeanPostProcessors = true;
		}
		this.beanPostProcessors.add(beanPostProcessor);
	}

	protected boolean hasInstantiationAwareBeanPostProcessors() {
		return this.hasInstantiationAwareBeanPostProcessors;
	}

}
