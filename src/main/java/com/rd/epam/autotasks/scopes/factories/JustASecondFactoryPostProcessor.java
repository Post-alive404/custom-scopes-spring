package com.rd.epam.autotasks.scopes.factories;

import com.rd.epam.autotasks.scopes.scope.JustASecondScope;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author Denys Parshutkin
 * @version 1.0.0
 */
public class JustASecondFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        configurableListableBeanFactory.registerScope("justASecond", new JustASecondScope());
    }
}
