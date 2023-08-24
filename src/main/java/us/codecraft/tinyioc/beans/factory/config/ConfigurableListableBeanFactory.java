package us.codecraft.tinyioc.beans.factory.config;

import us.codecraft.tinyioc.beans.BeanDefinition;

public interface ConfigurableListableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName);

    void preInstantiateSingletons();

}
