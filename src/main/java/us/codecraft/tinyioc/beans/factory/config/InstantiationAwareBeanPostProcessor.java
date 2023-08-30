package us.codecraft.tinyioc.beans.factory.config;

import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.BeansException;

/**
 * InstantiationAwareBeanPostProcessor
 *
 * @author 郭佳俊
 * @since 2023/8/30
 **/
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    default Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    default boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

}
