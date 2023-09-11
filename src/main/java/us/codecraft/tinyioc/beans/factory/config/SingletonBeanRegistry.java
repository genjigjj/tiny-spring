package us.codecraft.tinyioc.beans.factory.config;

/**
 * SingletonBeanRegistry
 *
 * @author 郭佳俊
 * @since 2023/8/29
 **/
public interface SingletonBeanRegistry {

    Object getSingleton(String beanName);

    boolean containsSingleton(String beanName);

}
