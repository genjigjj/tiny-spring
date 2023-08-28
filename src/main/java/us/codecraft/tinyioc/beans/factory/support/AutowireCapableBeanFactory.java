package us.codecraft.tinyioc.beans.factory.support;

import us.codecraft.tinyioc.beans.factory.BeanFactory;

/**
 * 可自动装配内容的BeanFactory
 *
 * @author yihua.huang@dianping.com
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object initializeBean(Object existingBean, String beanName) throws Exception;

    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws Exception;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws Exception;

}
