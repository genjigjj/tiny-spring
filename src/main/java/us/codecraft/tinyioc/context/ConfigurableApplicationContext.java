package us.codecraft.tinyioc.context;

import us.codecraft.tinyioc.beans.factory.config.ConfigurableListableBeanFactory;

public interface ConfigurableApplicationContext extends ApplicationContext {

    void refresh();

    void close();

    ConfigurableListableBeanFactory getBeanFactory();
}
