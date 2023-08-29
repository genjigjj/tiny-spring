package us.codecraft.tinyioc.context;

import us.codecraft.tinyioc.beans.BeansException;
import us.codecraft.tinyioc.beans.factory.DefaultListableBeanFactory;
import us.codecraft.tinyioc.beans.factory.config.ConfigurableListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private volatile DefaultListableBeanFactory beanFactory;

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws Exception;

    @Override
    protected void refreshBeanFactory() throws BeansException{
        try {
            DefaultListableBeanFactory beanFactory = createBeanFactory();
            loadBeanDefinitions(beanFactory);
            this.beanFactory = beanFactory;
        } catch (Exception e) {
            throw new BeansException("refreshBeanFactory error", e);
        }
    }

    @Override
    public ConfigurableListableBeanFactory getBeanFactory() {
        return this.beanFactory;
    }

    protected DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }
}
