package com.inventory.manager.domain.util;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/*
 * This replaces servlet-context.xml
 * @ImportResource loads property files. I have been unable to replace this with java 
 * config (ie this class), as you have to register a static bean to configure the property files. 
 * The problem is that I can't seem to retrieve environment variables statically as well, 
 * making it impossible to build the path to the property files.
 * */
@ImportResource("classpath:property-config.xml")
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.inventory.manager" },
excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.inventory.manager.infrastructure.*"))
public class TestAppConfig extends WebMvcConfigurerAdapter {
    
}