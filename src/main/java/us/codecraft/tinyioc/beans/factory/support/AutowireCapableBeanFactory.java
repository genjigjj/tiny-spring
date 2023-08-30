package us.codecraft.tinyioc.beans.factory.support;

import us.codecraft.tinyioc.beans.factory.BeanFactory;

/**
 * {@link BeanFactory} 接口的扩展，由能够自动装配的 bean 工厂实现，前提是它们希望为现有 bean 实例公开此功能。
 *
 * @author yihua.huang@dianping.com
 */
public interface AutowireCapableBeanFactory extends BeanFactory {

    Object initializeBean(Object existingBean, String beanName) throws Exception;

    Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws Exception;

    Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws Exception;

}
