package us.codecraft.tinyioc.beans.xml;

import org.junit.Assert;
import org.junit.Test;
import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.factory.DefaultListableBeanFactory;
import us.codecraft.tinyioc.beans.io.ResourceLoader;

import java.util.Map;

/**
 * @author yihua.huang@dianping.com
 */
public class XmlBeanDefinitionReaderTest {

	@Test
	public void test() throws Exception {
		XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(new DefaultListableBeanFactory());
		xmlBeanDefinitionReader.loadBeanDefinitions("tinyioc.xml");
	}
}
