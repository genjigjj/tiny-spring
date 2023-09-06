package us.codecraft.tinyioc.aop.advice;

import us.codecraft.tinyioc.aop.ThrowsAdvice;

/**
 * TestThrowsAdvice
 *
 * @author 郭佳俊
 * @since 2023/9/6
 **/
public class TestThrowsAdvice implements ThrowsAdvice {

    public void afterThrowing(Exception ex) throws Throwable {
        System.out.println("TestThrowsAdvice afterThrowing " + ex.getMessage());
    }

}
