package us.codecraft.tinyioc.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author yihua.huang@dianping.com
 */
public class ReflectiveMethodInvocation implements MethodInvocation {

	protected final List<?> interceptorsAndDynamicMethodMatchers;

	protected Object target;

    protected Method method;

    protected Object[] arguments;

	private int currentInterceptorIndex = -1;

	public ReflectiveMethodInvocation(Object target, Method method, Object[] arguments, List<?> interceptorsAndDynamicMethodMatchers) {
		this.target = target;
		this.method = method;
		this.arguments = arguments;
		this.interceptorsAndDynamicMethodMatchers = interceptorsAndDynamicMethodMatchers;
	}

	@Override
	public Method getMethod() {
		return method;
	}

	@Override
	public Object[] getArguments() {
		return arguments;
	}

	@Override
	public Object proceed() throws Throwable {
		if (this.currentInterceptorIndex == this.interceptorsAndDynamicMethodMatchers.size() - 1) {
			return invokeJoinpoint();
		}
		Object interceptorOrInterceptionAdvice =
				this.interceptorsAndDynamicMethodMatchers.get(++this.currentInterceptorIndex);
		return ((MethodInterceptor) interceptorOrInterceptionAdvice).invoke(this);
	}

	protected Object invokeJoinpoint() throws Throwable {
		return AopUtils.invokeJoinpointUsingReflection(this.target, this.method, this.arguments);
	}

	@Override
	public Object getThis() {
		return target;
	}

	@Override
	public AccessibleObject getStaticPart() {
		return method;
	}
}
