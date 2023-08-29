package us.codecraft.tinyioc.beans.factory.support;

import us.codecraft.tinyioc.beans.BeansException;
import us.codecraft.tinyioc.beans.factory.ObjectFactory;
import us.codecraft.tinyioc.beans.factory.config.SingletonBeanRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * DefaultSingletonBeanRegistry
 *
 * @author 郭佳俊
 * @since 2023/8/29
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {

    /**
     * Cache of singleton objects: bean name --> bean instance
     */
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    /** Cache of singleton factories: bean name --> ObjectFactory */
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

    /**
     * Cache of early singleton objects: bean name --> bean instance
     */
    private final Map<String, Object> earlySingletonObjects = new HashMap<>(16);

    /**
     * Set of registered singletons, containing the bean names in registration order
     */
    private final Set<String> registeredSingletons = new LinkedHashSet<>(256);

    /**
     * Names of beans that are currently in creation
     */
    private final Set<String> singletonsCurrentlyInCreation =
            Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    /**
     * Names of beans currently excluded from in creation checks
     */
    private final Set<String> inCreationCheckExclusions =
            Collections.newSetFromMap(new ConcurrentHashMap<>(16));


    /**
     * Disposable bean instances: bean name --> disposable instance
     */
    private final Map<String, Object> disposableBeans = new LinkedHashMap<>();

    /**
     * Map between containing bean names: bean name --> Set of bean names that the bean contains
     */
    private final Map<String, Set<String>> containedBeanMap = new ConcurrentHashMap<>(16);

    /**
     * Map between dependent bean names: bean name --> Set of dependent bean names
     */
    private final Map<String, Set<String>> dependentBeanMap = new ConcurrentHashMap<>(64);

    /**
     * Map between depending bean names: bean name --> Set of bean names for the bean's dependencies
     */
    private final Map<String, Set<String>> dependenciesForBeanMap = new ConcurrentHashMap<>(64);

    @Override
    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }

    protected Object getSingleton(String beanName, boolean allowEarlyReference) {
        Object singletonObject = this.singletonObjects.get(beanName);
        if (singletonObject == null && isSingletonCurrentlyInCreation(beanName)) {
            synchronized (this.singletonObjects) {
                singletonObject = this.earlySingletonObjects.get(beanName);
                if (singletonObject == null && allowEarlyReference) {
                    ObjectFactory<?> singletonFactory = this.singletonFactories.get(beanName);
                    if (singletonFactory != null) {
                        singletonObject = singletonFactory.getObject();
                        this.earlySingletonObjects.put(beanName, singletonObject);
                        this.singletonFactories.remove(beanName);
                    }
                }
            }
        }
        return singletonObject;
    }

    public Object getSingleton(String beanName, ObjectFactory<?> singletonFactory) {
        synchronized (this.singletonObjects) {
            Object singletonObject = this.singletonObjects.get(beanName);
            if (singletonObject == null) {
                beforeSingletonCreation(beanName);
                boolean newSingleton = false;
                try {
                    singletonObject = singletonFactory.getObject();
                    newSingleton = true;
                } catch (IllegalStateException ex) {
                    // Has the singleton object implicitly appeared in the meantime ->
                    // if yes, proceed with it since the exception indicates that state.
                    singletonObject = this.singletonObjects.get(beanName);
                    if (singletonObject == null) {
                        throw ex;
                    }
                } catch (BeansException ex) {
                    throw ex;
                } finally {
                    afterSingletonCreation(beanName);
                }
                if (newSingleton) {
                    addSingleton(beanName, singletonObject);
                }
            }
            return singletonObject;
        }
    }

    protected void addSingletonFactory(String beanName, ObjectFactory<?> singletonFactory) {
        synchronized (this.singletonObjects) {
            if (!this.singletonObjects.containsKey(beanName)) {
                this.singletonFactories.put(beanName, singletonFactory);
                this.earlySingletonObjects.remove(beanName);
                this.registeredSingletons.add(beanName);
            }
        }
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.put(beanName, singletonObject);
            this.earlySingletonObjects.remove(beanName);
            this.registeredSingletons.add(beanName);
        }
    }

    protected void afterSingletonCreation(String beanName) {
        if (!this.inCreationCheckExclusions.contains(beanName) && !this.singletonsCurrentlyInCreation.remove(beanName)) {
            throw new IllegalStateException("Singleton '" + beanName + "' isn't currently in creation");
        }
    }

    protected void beforeSingletonCreation(String beanName) {
        if (!this.inCreationCheckExclusions.contains(beanName) && !this.singletonsCurrentlyInCreation.add(beanName)) {
            throw new BeansException(beanName);
        }
    }


    public boolean isSingletonCurrentlyInCreation(String beanName) {
        return this.singletonsCurrentlyInCreation.contains(beanName);
    }
}
