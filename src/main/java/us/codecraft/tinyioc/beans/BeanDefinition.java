package us.codecraft.tinyioc.beans;

import us.codecraft.tinyioc.beans.factory.config.ConfigurableBeanFactory;

/**
 * bean的内容及元数据，保存在BeanFactory中，包装bean的实体
 * 
 * @author yihua.huang@dianping.com
 */
public class BeanDefinition {

	private static final String SCOPE_SINGLETON = ConfigurableBeanFactory.SCOPE_SINGLETON;

	private static final String SCOPE_DEFAULT = "";
	private static final String SCOPE_PROTOTYPE = ConfigurableBeanFactory.SCOPE_PROTOTYPE;

	private String scope = SCOPE_DEFAULT;

	private Class beanClass;

	private String beanClassName;

	private PropertyValues propertyValues = new PropertyValues();

	public BeanDefinition() {
	}

	public Class getBeanClass() {
		return beanClass;
	}

	public void setBeanClass(Class beanClass) {
		this.beanClass = beanClass;
	}

	public String getBeanClassName() {
		return beanClassName;
	}

	public void setBeanClassName(String beanClassName) {
		this.beanClassName = beanClassName;
		try {
			this.beanClass = Class.forName(beanClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public PropertyValues getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(PropertyValues propertyValues) {
		this.propertyValues = propertyValues;
	}

	public boolean isSingleton() {
		return SCOPE_SINGLETON.equals(this.scope) || SCOPE_DEFAULT.equals(this.scope);
	}

	public boolean isPrototype() {
		return SCOPE_PROTOTYPE.equals(this.scope);
	}
}
