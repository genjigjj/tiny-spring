package us.codecraft.tinyioc.context;

import us.codecraft.tinyioc.beans.factory.DefaultListableBeanFactory;

public interface ConfigurableApplicationContext extends ApplicationContext {

    void refresh() throws Exception;

    void close();

    DefaultListableBeanFactory getBeanFactory();
}
