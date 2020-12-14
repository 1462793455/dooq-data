package cc.dooq.data.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author 蛋清
 * @Description:
 * @date 2020/12/11 11:55
 */
@Component
public class SpringUtils implements BeanFactoryAware {

    /** beanFactory */
    private static BeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        SpringUtils.beanFactory = beanFactory;
    }

    /**
     * 依赖查找方式获取 Bean
    */
    public static <T> T getBean(String var1, Class<T> var2){
        return beanFactory.getBean(var1,var2);
    }

}
