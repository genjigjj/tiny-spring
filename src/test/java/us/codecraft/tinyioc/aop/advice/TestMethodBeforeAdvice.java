package us.codecraft.tinyioc.aop.advice;

import us.codecraft.tinyioc.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

/**
 * LogMethodBeforeAdvice
 *
 * @author 郭佳俊
 * @since 2023/9/6
 **/

public class TestMethodBeforeAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] args, Object target) throws Throwable {
        System.out.println("TestMethodBeforeAdvice invoke before " + method.getName());
    }
}
