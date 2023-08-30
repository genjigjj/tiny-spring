package us.codecraft.tinyioc.aop;

import org.aopalliance.aop.Advice;

/**
 *
 * 包含 AOP <b>advice<b> （在连接点采取的操作）和确定通知适用性的过滤器（例如切入点）的基本接口。
 * <i>此接口不供 Spring 用户使用，而是为了支持不同类型通知的通用性。<i>
 *
 * <p>Spring AOP 基于通过方法<b>拦截<b>传递的<b>围绕通知<b>，符合AOP联盟拦截API。
 * Advisor 接口允许支持不同类型的通知，例如 <b>before<b> 和 <b>after<b> 通知，这些通知不需要使用拦截来实现。
 *
 * @author yihua.huang@dianping.com
 */
public interface Advisor {

    /**
     * 在 Spring 中，"Advice" 是指切面（Aspect）中的方法或逻辑，它用于在特定的切点（Join Point）前后执行一些操作
     * @return Advice 通知
     */
    Advice getAdvice();
}
