package us.codecraft.tinyioc.beans;

import us.codecraft.tinyioc.beans.factory.support.BeanDefinitionRegistry;
import us.codecraft.tinyioc.beans.io.ResourceLoader;

/**
 * 从配置中读取BeanDefinition
 * @author yihua.huang@dianping.com
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(String location) throws Exception;
}
