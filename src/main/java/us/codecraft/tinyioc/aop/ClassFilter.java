package us.codecraft.tinyioc.aop;

/**
 * @author yihua.huang@dianping.com
 */
public interface ClassFilter {

    ClassFilter TRUE = TrueClassFilter.INSTANCE;

    boolean matches(Class<?> targetClass);
}
