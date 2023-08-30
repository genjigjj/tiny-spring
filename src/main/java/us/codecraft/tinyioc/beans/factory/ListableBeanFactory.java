package us.codecraft.tinyioc.beans.factory;

/**
 * 由 bean 工厂实现的 {@link BeanFactory} 接口的扩展，可以枚举所有 bean 实例，
 * 而不是按照客户端的要求尝试通过名称一一查找 bean。预加载所有 bean 定义的 BeanFactory 实现（例如基于 XML 的工厂）可以实现此接口。
 *
 * @author 郭佳俊
 * @since 2023/8/30
 **/
public interface ListableBeanFactory extends BeanFactory{

    String[] getBeanDefinitionNames();

    String[] getBeanNamesForType(Class<?> type);

}
