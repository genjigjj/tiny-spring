package us.codecraft.tinyioc.aop.adapter;

import org.aopalliance.intercept.MethodInterceptor;
import us.codecraft.tinyioc.aop.Advisor;

/**
 * AdvisorAdapterRegistry
 *
 * @author 郭佳俊
 * @since 2023/8/31
 **/
public interface AdvisorAdapterRegistry {

    Advisor wrap(Object advice);

    /**
     * Return an array of AOP Alliance MethodInterceptors to allow use of the
     * given Advisor in an interception-based framework.
     * <p>Don't worry about the pointcut associated with the {@link Advisor}, if it is
     * a {@link org.springframework.aop.PointcutAdvisor}: just return an interceptor.
     * @param advisor Advisor to find an interceptor for
     * @return an array of MethodInterceptors to expose this Advisor's behavior
     * @throws UnknownAdviceTypeException if the Advisor type is
     * not understood by any registered AdvisorAdapter
     */
    MethodInterceptor[] getInterceptors(Advisor advisor);

    /**
     * Register the given {@link AdvisorAdapter}. Note that it is not necessary to register
     * adapters for an AOP Alliance Interceptors or Spring Advices: these must be
     * automatically recognized by an {@code AdvisorAdapterRegistry} implementation.
     * @param adapter AdvisorAdapter that understands particular Advisor or Advice types
     */
    void registerAdvisorAdapter(AdvisorAdapter adapter);

}
