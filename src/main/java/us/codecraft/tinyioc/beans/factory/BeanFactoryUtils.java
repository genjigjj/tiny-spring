package us.codecraft.tinyioc.beans.factory;

/**
 * BeanFactoryUtils
 *
 * @author 郭佳俊
 * @since 2023/9/4
 **/
public class BeanFactoryUtils {

    /**
     * Get all bean names for the given type, including those defined in ancestor
     * factories. Will return unique names in case of overridden bean definitions.
     * <p>Does consider objects created by FactoryBeans if the "allowEagerInit"
     * flag is set, which means that FactoryBeans will get initialized. If the
     * object created by the FactoryBean doesn't match, the raw FactoryBean itself
     * will be matched against the type. If "allowEagerInit" is not set,
     * only raw FactoryBeans will be checked (which doesn't require initialization
     * of each FactoryBean).
     * @param lbf the bean factory
     * <i>objects created by FactoryBeans</i> (or by factory methods with a
     * "factory-bean" reference) for the type check. Note that FactoryBeans need to be
     * eagerly initialized to determine their type: So be aware that passing in "true"
     * for this flag will initialize FactoryBeans and "factory-bean" references.
     * @param type the type that beans must match
     * @return the array of matching bean names, or an empty array if none
     */
    public static String[] beanNamesForTypeIncludingAncestors(
            ListableBeanFactory lbf, Class<?> type) {

        return lbf.getBeanNamesForType(type);
    }

}
