package us.codecraft.tinyioc.aop.adapter;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import us.codecraft.tinyioc.aop.Advisor;

/**
 * AdvisorAdapter
 *
 * @author 郭佳俊
 * @since 2023/8/31
 **/
public interface AdvisorAdapter {

    /**
     * Does this adapter understand this advice object? Is it valid to
     * invoke the {@code getInterceptors} method with an Advisor that
     * contains this advice as an argument?
     * @param advice an Advice such as a BeforeAdvice
     * @return whether this adapter understands the given advice object
     * @see #getInterceptor(org.springframework.aop.Advisor)
     * @see org.springframework.aop.BeforeAdvice
     */
    boolean supportsAdvice(Advice advice);

    /**
     * Return an AOP Alliance MethodInterceptor exposing the behavior of
     * the given advice to an interception-based AOP framework.
     * <p>Don't worry about any Pointcut contained in the Advisor;
     * the AOP framework will take care of checking the pointcut.
     * @param advisor the Advisor. The supportsAdvice() method must have
     * returned true on this object
     * @return an AOP Alliance interceptor for this Advisor. There's
     * no need to cache instances for efficiency, as the AOP framework
     * caches advice chains.
     */
    MethodInterceptor getInterceptor(Advisor advisor);

}
