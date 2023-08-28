package us.codecraft.tinyioc.beans.factory;

import com.sun.istack.internal.Nullable;
import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.BeansException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yihua.huang@dianping.com
 */
public abstract class AbstractBeanFactory implements BeanFactory {

//	private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
//
//	private final List<String> beanDefinitionNames = new ArrayList<String>();

	private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

	private List<BeanPostProcessor> beanPostProcessors = new ArrayList<BeanPostProcessor>();

	@Override
	public Object getBean(String name) throws BeansException {
		return doGetBean(name, null, null);
	}

	protected <T> T doGetBean(final String name, @Nullable final Class<T> requiredType,
							  @Nullable final Object[] args) throws BeansException {
		Object bean = singletonObjects.get(name);
		if (bean != null) {
			return (T) bean;
		}
		BeanDefinition beanDefinition = getBeanDefinition(name);
		bean = createBean(name, beanDefinition, args);
		singletonObjects.put(name, bean);
		return (T) bean;
	}

	protected abstract Object createBean(String beanName, BeanDefinition mbd, @Nullable Object[] args) throws BeansException;


	public List<BeanPostProcessor> getBeanPostProcessors() {
		return this.beanPostProcessors;
	}



	protected abstract BeanDefinition getBeanDefinition(String beanName);


	public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) throws Exception {
		this.beanPostProcessors.add(beanPostProcessor);
	}


}
