package us.codecraft.tinyioc.aop;

import org.aopalliance.intercept.Interceptor;
import org.aopalliance.intercept.MethodInterceptor;
import us.codecraft.tinyioc.aop.adapter.AdvisorAdapterRegistry;
import us.codecraft.tinyioc.aop.adapter.DefaultAdvisorAdapterRegistry;

import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 代理相关的元数据
 * @author yihua.huang@dianping.com
 */
public class AdvisedSupport {

    private List<Advisor> advisors = new ArrayList<>();

    private Advisor[] advisorArray = new Advisor[0];

    private List<Class<?>> interfaces = new ArrayList<>();

    private transient Map<MethodCacheKey, List<Object>> methodCache;


	private TargetSource targetSource;

    private MethodInterceptor methodInterceptor;

    private MethodMatcher methodMatcher;

    public AdvisedSupport(){
        this.methodCache = new ConcurrentHashMap<>(32);
    }

    public TargetSource getTargetSource() {
        return targetSource;
    }

    public void setTargetSource(TargetSource targetSource) {
        this.targetSource = targetSource;
    }

    public MethodInterceptor getMethodInterceptor() {
        return methodInterceptor;
    }

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    public MethodMatcher getMethodMatcher() {
        return methodMatcher;
    }

    public void setMethodMatcher(MethodMatcher methodMatcher) {
        this.methodMatcher = methodMatcher;
    }

    public void addAdvisors(Advisor... advisors) {
        addAdvisors(Arrays.asList(advisors));
    }

    public void addAdvisors(Collection<Advisor> advisors) {
        if (advisors != null && !advisors.isEmpty()) {
            for (Advisor advisor : advisors) {
                this.advisors.add(advisor);
            }
            this.advisorArray = this.advisors.toArray(new Advisor[0]);
        }
    }

    public List<Class<?>> getInterfaces() {
        return interfaces;
    }

    public List<Object> getInterceptorsAndDynamicInterceptionAdvice(Method method, Class<?> targetClass) {
        MethodCacheKey cacheKey = new MethodCacheKey(method);
        List<Object> cached = this.methodCache.get(cacheKey);
        if (cached == null) {
            // This is somewhat tricky... We have to process introductions first,
            // but we need to preserve order in the ultimate list.
            List<Object> interceptorList = new ArrayList<Object>(getAdvisors().length);
            Class<?> actualClass = (targetClass != null ? targetClass : method.getDeclaringClass());
            AdvisorAdapterRegistry registry = new DefaultAdvisorAdapterRegistry();
            for (Advisor advisor : getAdvisors()) {
                if (advisor instanceof PointcutAdvisor) {
                    // Add it conditionally.
                    PointcutAdvisor pointcutAdvisor = (PointcutAdvisor) advisor;
                    if ( pointcutAdvisor.getPointcut().getClassFilter().matches(actualClass)) {
                        MethodMatcher mm = pointcutAdvisor.getPointcut().getMethodMatcher();
                        if (mm.matches(method, actualClass)) {
                            MethodInterceptor[] interceptors = registry.getInterceptors(advisor);
                            interceptorList.addAll(Arrays.asList(interceptors));
                        }
                    }
                }
                else {
                    Interceptor[] interceptors = registry.getInterceptors(advisor);
                    interceptorList.addAll(Arrays.asList(interceptors));
                }
            }
            cached = interceptorList;
            this.methodCache.put(cacheKey, cached);
        }
        return cached;
    }

    public final Advisor[] getAdvisors() {
        return this.advisorArray;
    }



    private static final class MethodCacheKey implements Comparable<MethodCacheKey> {

        private final Method method;

        private final int hashCode;

        public MethodCacheKey(Method method) {
            this.method = method;
            this.hashCode = method.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            return (this == other || (other instanceof MethodCacheKey &&
                    this.method == ((MethodCacheKey) other).method));
        }

        @Override
        public int hashCode() {
            return this.hashCode;
        }

        @Override
        public String toString() {
            return this.method.toString();
        }

        @Override
        public int compareTo(MethodCacheKey other) {
            int result = this.method.getName().compareTo(other.method.getName());
            if (result == 0) {
                result = this.method.toString().compareTo(other.method.toString());
            }
            return result;
        }
    }
}
