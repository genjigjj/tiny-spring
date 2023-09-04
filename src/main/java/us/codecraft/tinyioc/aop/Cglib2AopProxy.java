package us.codecraft.tinyioc.aop;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author yihua.huang@dianping.com
 */
public class Cglib2AopProxy extends AbstractAopProxy {

	public Cglib2AopProxy(AdvisedSupport advised) {
		super(advised);
	}

	@Override
	public Object getProxy() {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(advised.getTargetSource().getTargetClass());
		enhancer.setInterfaces(advised.getTargetSource().getInterfaces());
		enhancer.setCallback(new DynamicAdvisedInterceptor(advised));
		Object enhanced = enhancer.create();
		return enhanced;
	}

	private static class DynamicAdvisedInterceptor implements MethodInterceptor {

		private AdvisedSupport advised;


		private DynamicAdvisedInterceptor(AdvisedSupport advised) {
			this.advised = advised;
		}

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			Object target = advised.getTargetSource().getTarget();
			List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, target.getClass());
			if (chain == null || chain.isEmpty()) {
				return proxy.invoke(target, args);
			} else {
				return new CglibMethodInvocation(target, method, args, chain, proxy).proceed();
			}
		}
	}

	private static class CglibMethodInvocation extends ReflectiveMethodInvocation {

		private final MethodProxy methodProxy;


		public CglibMethodInvocation(Object target, Method method, Object[] args, List<Object> interceptorsAndDynamicMethodMatchers, MethodProxy methodProxy) {
			super(target, method, args, interceptorsAndDynamicMethodMatchers);
			this.methodProxy = methodProxy;
		}

	}

}
