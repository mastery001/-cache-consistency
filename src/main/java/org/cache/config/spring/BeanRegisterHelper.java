package org.cache.config.spring;

import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;

public class BeanRegisterHelper {

	public static final String GENERATED_BEAN_NAME_SEPARATOR = "#";

	public static String generateBeanName(BeanDefinition definition, ApplicationContext applicationContext)
			throws BeanDefinitionStoreException {
		String generatedBeanName = definition.getBeanClassName();
		if (generatedBeanName == null) {
			if (definition.getParentName() != null) {
				generatedBeanName = definition.getParentName() + "$child";
			} else if (definition.getFactoryBeanName() != null) {
				generatedBeanName = definition.getFactoryBeanName() + "$created";
			}
		}
		if (!StringUtils.hasText(generatedBeanName)) {
			throw new BeanDefinitionStoreException("Unnamed bean definition specifies neither "
					+ "'class' nor 'parent' nor 'factory-bean' - can't generate bean name");
		}
		String id = generatedBeanName;
		int counter = -1;
		while (counter == -1 || applicationContext.containsBean(id)) {
			counter++;
			id = generatedBeanName + GENERATED_BEAN_NAME_SEPARATOR + counter;
		}
		return id;
	}
	
	
}
