package us.codecraft.tinyioc.aop;

/**
 * 核心 Spring 切入点抽象。 <p>切入点由{@link ClassFilter}和{@link MethodMatcher}组成。
 * 这些基本术语和切入点本身都可以组合起来构建组合（例如通过{@link org.springframework.aop.support.ComposablePointcut}）。
 * @author yihua.huang@dianping.com
 */
public interface Pointcut {

    ClassFilter getClassFilter();

    MethodMatcher getMethodMatcher();

    Pointcut TRUE = TruePointcut.INSTANCE;

}
