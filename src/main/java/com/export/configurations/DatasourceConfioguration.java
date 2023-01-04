package com.export.configurations;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.util.ReflectionUtils;

import net.ttddyy.dsproxy.listener.logging.Log4jLogLevel;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;


@Configuration
public class DatasourceConfioguration implements BeanPostProcessor {

    @Override
    @Nullable
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof DataSource) {
            ProxyFactory factory = new ProxyFactory(bean);
            factory.setProxyTargetClass(true);
            factory.addAdvice(new ProxyDataSourceInterceptor((DataSource) bean));
            return factory.getProxy();
        }
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
    @Override
    @Nullable
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

public static class ProxyDataSourceInterceptor implements MethodInterceptor {

    private final DataSource dataSource;

    public ProxyDataSourceInterceptor(DataSource dataSource) {
        super();
        this.dataSource = ProxyDataSourceBuilder.create(dataSource).countQuery().logQueryByLog4j(Log4jLogLevel.INFO).build();
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method methodProxy = ReflectionUtils.findMethod(this.dataSource.getClass(), invocation.getMethod().getName());
        if (methodProxy != null) {
            return methodProxy.invoke(this.dataSource, invocation.getArguments());
        }
        return invocation.proceed();
    }
}
}
