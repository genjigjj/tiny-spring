package us.codecraft.tinyioc.beans.factory;

import us.codecraft.tinyioc.beans.BeansException;
import us.codecraft.tinyioc.beans.factory.config.ConfigurableBeanFactory;

/**
 * 用于访问 Spring bean 容器的根接口。这是 Bean 容器的基本客户端视图；其他接口例如 {@link ListableBeanFactory} 和
 * {@link ConfigurableBeanFactory} 可用于特定目的。
 * @author yihua.huang@dianping.com
 */
public interface BeanFactory {

    String FACTORY_BEAN_PREFIX = "&";

    Object getBean(String name);

    <T> T getBean(String name, Class<T> requiredType) throws BeansException;

}
