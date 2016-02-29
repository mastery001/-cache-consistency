package org.cache.config.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;

/**
 * SpringBean管理器
 * 
 * @author zouziwen
 *
 *         2016年2月26日 下午1:31:31
 */
public class SpringBeanManager {

	private final ApplicationContext applicationContext;

	public SpringBeanManager(ApplicationContext applicationContext) {
		super();
		this.applicationContext = applicationContext;
	}

	public DefaultListableBeanFactory getDefaultBeanFactory() {
		return (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
	}

	public <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	public Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 注册bean并且得到bean的名称
	 * @param beanDefinition
	 * @return
	 * 2016年2月29日 下午2:01:19
	 */
	public String registerBeanWithGeneratedName(BeanDefinition beanDefinition) {
		String id = BeanRegisterHelper.generateBeanName(beanDefinition, applicationContext);
		getDefaultBeanFactory().registerBeanDefinition(id, beanDefinition);
		return id;
	}

	public String registerBeanWithGeneratedName(Class<?> cls) {
		return registerBeanWithGeneratedName(new RootBeanDefinition(cls));
	}

	public Object registerBean(BeanDefinition beanDefinition) {
		return applicationContext.getBean(registerBeanWithGeneratedName(beanDefinition));
	}

	public Object registerBean(Class<?> cls) {
		return registerBean(new RootBeanDefinition(cls));
	}
}
