package us.codecraft.tinyioc.context;

import us.codecraft.tinyioc.beans.factory.ListableBeanFactory;
import us.codecraft.tinyioc.beans.factory.support.AutowireCapableBeanFactory;
import us.codecraft.tinyioc.beans.io.ResourceLoader;

/**
 * @author yihua.huang@dianping.com
 */
public interface ApplicationContext extends ListableBeanFactory, ResourceLoader {

    AutowireCapableBeanFactory getAutowireCapableBeanFactory();

}
