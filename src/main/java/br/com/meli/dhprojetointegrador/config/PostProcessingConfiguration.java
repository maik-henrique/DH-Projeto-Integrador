package br.com.meli.dhprojetointegrador.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;

/**
 * @Author: Maik
 * Habilita a configuração do mecanismo de post-processing, usado para adicionar configurações adicionais após
 * a inicialização
 */
@Configuration
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class PostProcessingConfiguration implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            return new RetryDataSource((DataSource) bean);
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
