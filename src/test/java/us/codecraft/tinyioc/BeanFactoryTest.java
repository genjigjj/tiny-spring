package us.codecraft.tinyioc;

import org.junit.Test;
import us.codecraft.tinyioc.beans.factory.DefaultListableBeanFactory;
import us.codecraft.tinyioc.beans.xml.XmlBeanDefinitionReader;

/**
 * @author yihua.huang@dianping.com
 */
public class BeanFactoryTest {

    @Test
    public void testLazy() throws Exception {

        // 1、初始化BeanFactory并注册bean
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.读取配置
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");


        // 3.获取bean
        HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
        helloWorldService.helloWorld();
    }

	@Test
	public void testPreInstantiate() throws Exception {
        // 1、初始化BeanFactory并注册bean
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

		// 2.读取配置
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
		xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");

        // 3.初始化bean
        beanFactory.preInstantiateSingletons();

		// 4.获取bean
		HelloWorldService helloWorldService = (HelloWorldService) beanFactory.getBean("helloWorldService");
		helloWorldService.helloWorld();
	}
}
