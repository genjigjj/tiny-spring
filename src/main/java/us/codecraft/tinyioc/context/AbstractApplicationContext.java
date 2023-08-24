package us.codecraft.tinyioc.context;

import us.codecraft.tinyioc.beans.BeanPostProcessor;
import us.codecraft.tinyioc.beans.factory.AbstractBeanFactory;
import us.codecraft.tinyioc.beans.factory.support.AutowireCapableBeanFactory;
import us.codecraft.tinyioc.beans.factory.DefaultListableBeanFactory;
import us.codecraft.tinyioc.beans.io.DefaultResourceLoader;

import java.util.List;

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
    protected void registerBeanPostProcessors(AbstractBeanFactory beanFactory) throws Exception {
        List beanPostProcessors = beanFactory.getBeansForType(BeanPostProcessor.class);
        for (Object beanPostProcessor : beanPostProcessors) {
            beanFactory.addBeanPostProcessor((BeanPostProcessor) beanPostProcessor);
        }
    }

    protected void onRefresh() throws Exception {
        beanFactory.preInstantiateSingletons();
    }

    @Override
    public Object getBean(String name) throws Exception {
        return beanFactory.getBean(name);
    }

    protected DefaultListableBeanFactory obtainFreshBeanFactory() throws Exception {
        refreshBeanFactory();
        return getBeanFactory();
    }

    protected abstract void refreshBeanFactory() throws Exception;

    public abstract AutowireCapableBeanFactory getBeanFactory();
}
