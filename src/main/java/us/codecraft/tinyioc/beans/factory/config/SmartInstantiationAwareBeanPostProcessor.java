package us.codecraft.tinyioc.beans.factory.config;

import us.codecraft.tinyioc.beans.BeansException;

/**
 * SmartInstantiationAwareBeanPostProcessor
 *
 * @author 郭佳俊
 * @since 2023/8/30
 **/
public interface SmartInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessor{

    default Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
