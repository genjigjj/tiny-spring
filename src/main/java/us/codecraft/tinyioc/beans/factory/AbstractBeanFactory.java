package us.codecraft.tinyioc.beans.factory;

import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.BeansException;
import us.codecraft.tinyioc.beans.factory.config.ConfigurableBeanFactory;
import us.codecraft.tinyioc.beans.factory.config.InstantiationAwareBeanPostProcessor;
import us.codecraft.tinyioc.beans.factory.support.DefaultSingletonBeanRegistry;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yihua.huang@dianping.com
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

	private volatile boolean hasInstantiationAwareBeanPostProcessors;

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
		Object bean;
		Object sharedInstance = getSingleton(name);

		if (sharedInstance != null && args == null) {
			bean = sharedInstance;
		} else {
			BeanDefinition beanDefinition = getBeanDefinition(name);
			if (beanDefinition.isSingleton()) {
				sharedInstance = getSingleton(name, () -> createBean(name, beanDefinition, args));
				bean = sharedInstance;
			} else if (beanDefinition.isPrototype()) {
				bean = createBean(name, beanDefinition, args);
			} else {
				throw new BeansException("doGetBean error");
			}
		}
		return (T) bean;
	}

	protected abstract Object createBean(String beanName, BeanDefinition mbd,  Object[] args) throws BeansException;


	public List<BeanPostProcessor> getBeanPostProcessors() {
		return this.beanPostProcessors;
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
