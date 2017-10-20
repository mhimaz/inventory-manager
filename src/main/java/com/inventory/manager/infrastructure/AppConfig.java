package com.inventory.manager.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 * This replaces servlet-context.xml
 * @ImportResource loads property files. I have been unable to replace this with java 
 * config (ie this class), as you have to register a static bean to configure the property files. 
 * The problem is that I can't seem to retrieve environment variables statically as well, 
 * making it impossible to build the path to the property files.
 * */
@ImportResource({"classpath:property-config.xml"})
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan({"com.inventory.manager"})
public class AppConfig extends WebMvcConfigurerAdapter {

    @Value("#{systemEnvironment['INVENTORY_CONFIG']}")
    private String inventoryConfigPath;

    // Load message properties files.
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setResourceLoader(fsResourceLoader());
        source.setBasename("file:" + inventoryConfigPath + "/messages/validation-codes");
        return source;
    }

    @Bean
    public FileSystemResourceLoader fsResourceLoader() {
        return new FileSystemResourceLoader();
    }

    // Create new validator bean
    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean validator = new LocalValidatorFactoryBean();
        validator.setValidationMessageSource(messageSource());
        return validator;
    }

    // Tell app to use that bean
    @Override
    public Validator getValidator() {
        return validator();
    }
}
