package us.codecraft.tinyioc.context;

import us.codecraft.tinyioc.beans.factory.AbstractBeanFactory;
import us.codecraft.tinyioc.beans.factory.DefaultListableBeanFactory;
import us.codecraft.tinyioc.beans.xml.XmlBeanDefinitionReader;

/**
 * @author yihua.huang@dianping.com
 */
public class ClassPathXmlApplicationContext extends AbstractRefreshableApplicationContext {

	private String configLocation;

	public ClassPathXmlApplicationContext(String configLocation) throws Exception {
		this(configLocation, new DefaultListableBeanFactory());
	}

	public ClassPathXmlApplicationContext(String configLocation, AbstractBeanFactory beanFactory) throws Exception {
		this.configLocation = configLocation;
		refresh();
	}

	@Override
	protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws Exception {
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
		xmlBeanDefinitionReader.loadBeanDefinitions(configLocation);
	}
}
