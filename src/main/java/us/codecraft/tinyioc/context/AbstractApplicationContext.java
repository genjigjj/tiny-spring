package us.codecraft.tinyioc.context;

import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.BeansException;
import us.codecraft.tinyioc.beans.factory.config.ConfigurableListableBeanFactory;
import us.codecraft.tinyioc.beans.factory.support.AutowireCapableBeanFactory;
import us.codecraft.tinyioc.beans.io.DefaultResourceLoader;

/**
 * @author yihua.huang@dianping.com
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public AbstractApplicationContext() {
    }

    @Override
    public void refresh() {
        prepareRefresh();

        ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

        prepareBeanFactory(beanFactory);

        postProcessBeanFactory(beanFactory);

        try {
            invokeBeanFactoryPostProcessors(beanFactory);

            registerBeanPostProcessors(beanFactory);

            initMessageSource();

            initApplicationEventMulticaster();

            onRefresh();

            registerListeners();

            finishBeanFactoryInitialization(beanFactory);

            finishRefresh();
        } catch (BeansException ex) {
            // Destroy already created singletons to avoid dangling resources.
            destroyBeans();

            // Reset 'active' flag.
            cancelRefresh(ex);

            // Propagate exception to caller.
            throw ex;
        }

    }

    protected void prepareRefresh() {

    }

    protected ConfigurableListableBeanFactory obtainFreshBeanFactory() {
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected void prepareBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    protected void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {

    }

    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {

    }

    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class);
        for (String postProcessorName : postProcessorNames) {
            beanFactory.addBeanPostProcessor((BeanPostProcessor) getBean(postProcessorName));
        }
    }

    protected void initMessageSource() {

    }

    protected void initApplicationEventMulticaster() {

    }

    protected void onRefresh() {

    }

    protected void registerListeners() {

    }

    protected void finishBeanFactoryInitialization(ConfigurableListableBeanFactory beanFactory) {
        beanFactory.preInstantiateSingletons();
    }

    protected void finishRefresh() {

    }

    protected void cancelRefresh(BeansException ex) {

    }

    protected void destroyBeans() {

    }


    @Override
    public void close() {

    }

    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }


    protected abstract void refreshBeanFactory() throws BeansException;

    public abstract ConfigurableListableBeanFactory getBeanFactory();

    @Override
    public AutowireCapableBeanFactory getAutowireCapableBeanFactory() {
        return getBeanFactory();
    }
}
