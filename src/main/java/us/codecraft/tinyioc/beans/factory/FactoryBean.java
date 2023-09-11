package us.codecraft.tinyioc.beans.factory;

/**
 * FactoryBean
 *
 * @author 郭佳俊
 * @since 2023/9/11
 **/
public interface FactoryBean <T>{

    T getObject();

    Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }

}
