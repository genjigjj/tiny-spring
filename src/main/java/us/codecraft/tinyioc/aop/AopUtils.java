/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package us.codecraft.tinyioc.aop;

import com.sun.istack.internal.Nullable;
import us.codecraft.tinyioc.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for AOP support code.
 *
 * <p>Mainly for internal use within Spring's AOP support.
 *
 * <p>See {@link org.springframework.aop.framework.AopProxyUtils} for a
 * collection of framework-specific AOP utility methods which depend
 * on internals of Spring's AOP framework implementation.
 *
 * @author Rod Johnson
 * @author Juergen Hoeller
 * @author Rob Harrop
 * @see org.springframework.aop.framework.AopProxyUtils
 */
public abstract class AopUtils {


    /**
     * Can the given pointcut apply at all on the given class?
     * <p>This is an important test as it can be used to optimize
     * out a pointcut for a class.
     *
     * @param pc          the static or dynamic pointcut to check
     * @param targetClass the class to test
     *                    for this bean includes any introductions
     * @return whether the pointcut can apply on any method
     */
    public static boolean canApply(Pointcut pc, Class<?> targetClass) {
        if (!pc.getClassFilter().matches(targetClass)) {
            return false;
        }

        MethodMatcher methodMatcher = pc.getMethodMatcher();
        if (methodMatcher == MethodMatcher.TRUE) {
            // No need to iterate the methods if we're matching any method anyway...
            return true;
        }

        Method[] methods = ReflectionUtils.getAllDeclaredMethods(targetClass);
        for (Method method : methods) {
            if (methodMatcher.matches(method, targetClass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Can the given advisor apply at all on the given class?
     * This is an important test as it can be used to optimize
     * out a advisor for a class.
     *
     * @param advisor     the advisor to check
     * @param targetClass class we're testing
     * @return whether the pointcut can apply on any method
     */
    public static boolean canApply(Advisor advisor, Class<?> targetClass) {
        if (advisor instanceof PointcutAdvisor) {
            PointcutAdvisor pca = (PointcutAdvisor) advisor;
            return canApply(pca.getPointcut(), targetClass);
        } else {
            // It doesn't have a pointcut so we assume it applies.
            return true;
        }
    }


    /**
     * Determine the sublist of the {@code candidateAdvisors} list
     * that is applicable to the given class.
     *
     * @param candidateAdvisors the Advisors to evaluate
     * @param clazz             the target class
     * @return sublist of Advisors that can apply to an object of the given class
     * (may be the incoming List as-is)
     */
    public static List<Advisor> findAdvisorsThatCanApply(List<Advisor> candidateAdvisors, Class<?> clazz) {
        if (candidateAdvisors.isEmpty()) {
            return candidateAdvisors;
        }
        List<Advisor> eligibleAdvisors = new ArrayList<>();
        for (Advisor candidate : candidateAdvisors) {
            if (canApply(candidate, clazz)) {
                eligibleAdvisors.add(candidate);
            }
        }
        return eligibleAdvisors;
    }

    /**
     * Invoke the given target via reflection, as part of an AOP method invocation.
     *
     * @param target the target object
     * @param method the method to invoke
     * @param args   the arguments for the method
     * @return the invocation result, if any
     * @throws Throwable                                      if thrown by the target method
     * @throws org.springframework.aop.AopInvocationException in case of a reflection error
     */
    @Nullable
    public static Object invokeJoinpointUsingReflection(@Nullable Object target, Method method, Object[] args)
            throws Throwable {

        // Use reflection to invoke the method.
        try {
            ReflectionUtils.makeAccessible(method);
            return method.invoke(target, args);
        } catch (InvocationTargetException ex) {
            // Invoked method threw a checked exception.
            // We must rethrow it. The client won't see the interceptor.
            throw ex.getTargetException();
        } catch (IllegalArgumentException ex) {
            throw ex;
        } catch (IllegalAccessException ex) {
            throw ex;
        }
    }

}
