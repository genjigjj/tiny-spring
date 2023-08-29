package us.codecraft.tinyioc.context;

import us.codecraft.tinyioc.beans.factory.BeanFactory;
import us.codecraft.tinyioc.beans.factory.support.AutowireCapableBeanFactory;
import us.codecraft.tinyioc.beans.io.ResourceLoader;

/**
 * @author yihua.huang@dianping.com
 */
public interface ApplicationContext extends BeanFactory, ResourceLoader {

    AutowireCapableBeanFactory getAutowireCapableBeanFactory();

}
