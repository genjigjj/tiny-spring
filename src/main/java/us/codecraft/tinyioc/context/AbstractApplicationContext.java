package us.codecraft.tinyioc.context;

import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.factory.DefaultListableBeanFactory;
import us.codecraft.tinyioc.beans.factory.config.ConfigurableListableBeanFactory;
import us.codecraft.tinyioc.beans.io.DefaultResourceLoader;

/**
 * @author yihua.huang@dianping.com
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    public AbstractApplicationContext() {
    }

    public void refresh() throws Exception {
        DefaultListableBeanFactory beanFactory = obtainFreshBeanFactory();
        registerBeanPostProcessors(beanFactory);
        onRefresh();
    }
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) throws Exception {
        String[] postProcessorNames = beanFactory.getBeanNamesForType(BeanPostProcessor.class);
        for (String postProcessorName : postProcessorNames) {
            beanFactory.addBeanPostProcessor((BeanPostProcessor) getBean(postProcessorName));
        }
    }

    protected void onRefresh() throws Exception {
        getBeanFactory().preInstantiateSingletons();
    }

    @Override
    public Object getBean(String name) throws Exception {
        return getBeanFactory().getBean(name);
    }

    protected DefaultListableBeanFactory obtainFreshBeanFactory() throws Exception {
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected abstract void refreshBeanFactory() throws Exception;

    public abstract DefaultListableBeanFactory getBeanFactory();
}
