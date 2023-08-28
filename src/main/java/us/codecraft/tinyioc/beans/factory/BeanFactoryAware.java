package us.codecraft.tinyioc.beans.factory;

/**
 * BeanFactoryAware
 *
 * @author 郭佳俊
 * @since 2023/8/28
 **/
public interface BeanFactoryAware {

    void setBeanFactory(BeanFactory beanFactory);

}
