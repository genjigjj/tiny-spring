package us.codecraft.tinyioc.beans.factory;

import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.BeansException;
import us.codecraft.tinyioc.beans.factory.config.ConfigurableListableBeanFactory;
import us.codecraft.tinyioc.beans.factory.support.BeanDefinitionRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring 的 {@link ConfigurableListableBeanFactory} 和 {@link BeanDefinitionRegistry} 接口的默认实现：
 * 基于 bean 定义元数据的成熟 bean 工厂，可通过后处理器进行扩展。
 */
public class DefaultListableBeanFactory extends AbstractAutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    private List<String> beanDefinitionNames = new ArrayList<>(256);


    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
        beanDefinitionMap.put(beanName, beanDefinition);
        beanDefinitionNames.add(beanName);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new BeansException("No bean named " + beanName + " is defined");
        }
        return beanDefinition;
    }

    @Override
    public void preInstantiateSingletons() {
        for (String beanName : beanDefinitionNames) {
            if (isFactoryBean(beanName)) {

            } else {
                getBean(beanName);
            }
        }
    }

    @Override
    public boolean isFactoryBean(String name) {
        Object beanInstance = getSingleton(name, false);
        if (beanInstance != null) {
            return (beanInstance instanceof FactoryBean);
        }
        BeanDefinition beanDefinition = getBeanDefinition(name);
        return beanDefinition.getBeanClass().isAssignableFrom(FactoryBean.class);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionNames.toArray(new String[0]);
    }

    @Override
    public String[] getBeanNamesForType(Class<?> type) {
        return doGetBeanNamesForType(type);
    }

    private String[] doGetBeanNamesForType(Class<?> type) {
        List<String> beans = new ArrayList<>();
        for (String beanDefinitionName : beanDefinitionNames) {
            if (type.isAssignableFrom(beanDefinitionMap.get(beanDefinitionName).getBeanClass())) {
                beans.add(beanDefinitionName);
            }
        }
        return beans.toArray(new String[0]);

    }


}
