package us.codecraft.tinyioc.context;

import us.codecraft.tinyioc.beans.factory.DefaultListableBeanFactory;
import us.codecraft.tinyioc.beans.xml.XmlBeanDefinitionReader;

/**
 * @author yihua.huang@dianping.com
 */
public class ClassPathXmlApplicationContext extends AbstractRefreshableApplicationContext {

    private String configLocation;

    public ClassPathXmlApplicationContext(String configLocation) {
        this(configLocation, true);
    }

    public ClassPathXmlApplicationContext(String configLocation, boolean refresh) {
        this.configLocation = configLocation;
        if (refresh) {
            refresh();
        }
    }

    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws Exception {
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
    }
}
