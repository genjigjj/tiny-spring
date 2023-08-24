package us.codecraft.tinyioc.beans.factory;

import us.codecraft.tinyioc.beans.BeanDefinition;
import us.codecraft.tinyioc.beans.factory.config.ConfigurableListableBeanFactory;
import us.codecraft.tinyioc.beans.factory.support.AutowireCapableBeanFactory;
import us.codecraft.tinyioc.beans.factory.support.BeanDefinitionRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory extends AutowireCapableBeanFactory implements BeanDefinitionRegistry, ConfigurableListableBeanFactory {

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
            throw new IllegalArgumentException("No bean named " + beanName + " is defined");
        }
        return beanDefinition;
    }


}
