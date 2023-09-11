package us.codecraft.tinyioc;

import us.codecraft.tinyioc.beans.factory.FactoryBean;

/**
 * TestFactoryBean
 *
 * @author 郭佳俊
 * @since 2023/9/11
 **/
public class TestFactoryBean implements FactoryBean<TestServiceImpl> {
    @Override
    public TestServiceImpl getObject() {
        return new TestServiceImpl();
    }

    @Override
    public Class<?> getObjectType() {
        return TestServiceImpl.class;
    }
}
