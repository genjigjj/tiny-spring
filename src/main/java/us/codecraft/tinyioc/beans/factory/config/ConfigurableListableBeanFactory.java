package us.codecraft.tinyioc.beans.factory.config;

import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.factory.BeanFactory;
import us.codecraft.tinyioc.beans.factory.support.AutowireCapableBeanFactory;

public interface ConfigurableListableBeanFactory extends BeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory{

    BeanDefinition getBeanDefinition(String beanName);

    void preInstantiateSingletons();

    String[] getBeanNamesForType(Class<?> type);

    void addBeanPostProcessor(BeanPostProcessor bean);
}
