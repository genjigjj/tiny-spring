package us.codecraft.tinyioc.beans.factory.support;

import us.codecraft.tinyioc.beans.BeanDefinition;

/**
 * Interface for registries that hold bean definitions, for example RootBeanDefinition
 * and ChildBeanDefinition instances. Typically implemented by BeanFactories that
 * internally work with the AbstractBeanDefinition hierarchy.
 */
public interface BeanDefinitionRegistry {

    /**
     * Register a new bean definition with this registry.
     * Must support RootBeanDefinition and ChildBeanDefinition.
     *
     * @param beanName       the name of the bean instance to register
     * @param beanDefinition definition of the bean instance to register
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
