package us.codecraft.tinyioc.beans.factory.config;

import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory {

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
