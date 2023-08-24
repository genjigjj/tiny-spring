package us.codecraft.tinyioc.beans;

import us.codecraft.tinyioc.beans.factory.support.BeanDefinitionRegistry;
import us.codecraft.tinyioc.beans.io.DefaultResourceLoader;
import us.codecraft.tinyioc.beans.io.ResourceLoader;

import java.util.HashMap;
import java.util.Map;

/**
 * 从配置中读取BeanDefinition
 *
 * @author yihua.huang@dianping.com
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
        this.resourceLoader = new DefaultResourceLoader();
    }

    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
