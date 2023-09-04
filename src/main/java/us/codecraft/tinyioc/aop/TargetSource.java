package us.codecraft.tinyioc.aop;

/**
 * 被代理的对象
 * @author yihua.huang@dianping.com
 */
public class TargetSource {

	private Class<?> targetClass;

    private Class<?>[] interfaces;

	private Object target;

	public TargetSource(Object target, Class<?> targetClass,Class<?>... interfaces) {
		this.target = target;
		this.targetClass = targetClass;
        this.interfaces = interfaces;
	}

	public TargetSource(Object target) {
		this.target = target;
		this.targetClass = target.getClass();
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Object getTarget() {
		return target;
	}

    public Class<?>[] getInterfaces() {
        return interfaces;
    }
}
