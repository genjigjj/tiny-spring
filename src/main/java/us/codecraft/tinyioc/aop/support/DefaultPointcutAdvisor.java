package us.codecraft.tinyioc.aop.support;

import org.aopalliance.aop.Advice;
import us.codecraft.tinyioc.aop.AbstractGenericPointcutAdvisor;
import us.codecraft.tinyioc.aop.Pointcut;

/**
 * DefaultPointcutAdvisor
 *
 * @author 郭佳俊
 * @since 2023/8/31
 **/
public class DefaultPointcutAdvisor extends AbstractGenericPointcutAdvisor {

    private Pointcut pointcut = Pointcut.TRUE;

    public DefaultPointcutAdvisor(Advice advice) {
        this(Pointcut.TRUE, advice);
    }

    public DefaultPointcutAdvisor(Pointcut pointcut, Advice advice) {
        this.pointcut = pointcut;
        setAdvice(advice);
    }


    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
