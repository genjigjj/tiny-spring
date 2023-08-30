package us.codecraft.tinyioc.beans.factory.config;

import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.factory.ListableBeanFactory;
import us.codecraft.tinyioc.beans.factory.support.AutowireCapableBeanFactory;

/**
 * 大多数集合类bean 工厂都会实现配置接口。除了 ConfigurableBeanFactory 之外，它还提供了分析和修改 bean 定义以及预实例化单例的工具。
 */
public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory{

    BeanDefinition getBeanDefinition(String beanName);

    void preInstantiateSingletons();

}
