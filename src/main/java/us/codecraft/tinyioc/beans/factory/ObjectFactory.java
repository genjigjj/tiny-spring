package us.codecraft.tinyioc.beans.factory;

import us.codecraft.tinyioc.beans.BeansException;

/**
 * ObjectFactory
 *
 * @author 郭佳俊
 * @since 2023/8/29
 **/
public interface ObjectFactory<T> {

    T getObject() throws BeansException;

}
