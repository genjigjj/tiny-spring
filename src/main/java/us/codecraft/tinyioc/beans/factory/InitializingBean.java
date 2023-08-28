package us.codecraft.tinyioc.beans.factory;

/**
 * InitializingBean
 *
 * @author 郭佳俊
 * @since 2023/8/28
 **/
public interface InitializingBean {

    void afterPropertiesSet();

}
