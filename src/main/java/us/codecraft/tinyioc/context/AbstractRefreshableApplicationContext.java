package us.codecraft.tinyioc.context;

import us.codecraft.tinyioc.beans.factory.AbstractBeanFactory;
import us.codecraft.tinyioc.beans.factory.DefaultListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private volatile DefaultListableBeanFactory beanFactory;

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws Exception;

    @Override
    protected void refreshBeanFactory() throws Exception {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    @Override
    public void close() {

    }

    @Override
    public DefaultListableBeanFactory getBeanFactory() {
        return null;
    }

    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }
}
