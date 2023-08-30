package us.codecraft.tinyioc.aop;

/**
 * 由切入点驱动的所有 Advisor 的超级接口。这涵盖了除介绍顾问之外的几乎所有顾问，方法级别匹配不适用。
 *
 * @author yihua.huang@dianping.com
 */
public interface PointcutAdvisor extends Advisor {

    Pointcut getPointcut();
}
