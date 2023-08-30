package us.codecraft.tinyioc.aop;

import org.aopalliance.intercept.MethodInterceptor;
import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.factory.AbstractBeanFactory;
import us.codecraft.tinyioc.beans.factory.BeanFactory;
import us.codecraft.tinyioc.beans.factory.BeanFactoryAware;

/**
 * @author yihua.huang@dianping.com
 */
public class AspectJAwareAdvisorAutoProxyCreator implements BeanPostProcessor, BeanFactoryAware {

	private AbstractBeanFactory beanFactory;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) {
		if (bean instanceof AspectJExpressionPointcutAdvisor) {
			return bean;
		}
		if (bean instanceof MethodInterceptor) {
			return bean;
		}
//		List<AspectJExpressionPointcutAdvisor> advisors = beanFactory
//				.getBeansForType(AspectJExpressionPointcutAdvisor.class);
//		for (AspectJExpressionPointcutAdvisor advisor : advisors) {
//			if (advisor.getPointcut().getClassFilter().matches(bean.getClass())) {
//                ProxyFactory advisedSupport = new ProxyFactory();
//				advisedSupport.setMethodInterceptor((MethodInterceptor) advisor.getAdvice());
//				advisedSupport.setMethodMatcher(advisor.getPointcut().getMethodMatcher());
//
//				TargetSource targetSource = new TargetSource(bean, bean.getClass(), bean.getClass().getInterfaces());
//				advisedSupport.setTargetSource(targetSource);
//
//				return advisedSupport.getProxy();
//			}
//		}
		return bean;
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = (AbstractBeanFactory) beanFactory;
	}
}
