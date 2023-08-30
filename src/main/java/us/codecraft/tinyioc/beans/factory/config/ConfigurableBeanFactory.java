package us.codecraft.tinyioc.beans.factory.config;

import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.factory.BeanFactory;

/**
 * 大多数 bean 工厂要实现的配置接口。除了 {@link BeanFactory} 接口中的 bean 工厂客户端方法之外，还提供配置 bean 工厂的工具。
 */
public interface ConfigurableBeanFactory extends BeanFactory {

    /**
     * Scope identifier for the standard singleton scope: "singleton".
     * Custom scopes can be added via {@code registerScope}.
     */
    String SCOPE_SINGLETON = "singleton";

    /**
     * Scope identifier for the standard prototype scope: "prototype".
     * Custom scopes can be added via {@code registerScope}.
     */
    String SCOPE_PROTOTYPE = "prototype";

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
