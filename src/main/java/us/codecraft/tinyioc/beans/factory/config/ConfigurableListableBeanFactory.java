package us.codecraft.tinyioc.beans.factory.config;

import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.BeanPostProcessor;

public interface ConfigurableListableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName);

    void preInstantiateSingletons();

    String[] getBeanNamesForType(Class<?> type);

    void addBeanPostProcessor(BeanPostProcessor bean);
}
