package us.codecraft.tinyioc.aop.advice;

import us.codecraft.tinyioc.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

/**
 * TestAfterReturningAdvice
 *
 * @author 郭佳俊
 * @since 2023/9/6
 **/
public class TestAfterReturningAdvice implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
        System.out.println("TestAfterReturningAdvice afterReturning method: " + method.getName() + ", returnValue is " + returnValue);
    }
}
