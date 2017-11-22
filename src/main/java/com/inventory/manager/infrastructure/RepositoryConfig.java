package com.inventory.manager.infrastructure;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableJpaRepositories("com.inventory.manager.domain")
@EnableJpaAuditing
public class RepositoryConfig {

    private static final Logger logger = Logger.getLogger(RepositoryConfig.class);

    @Value("${inventory.manager.mysql.db.driver}")
    private String databaseDriver = null;

    @Value("${inventory.manager.mysql.db.clienthosturl}")
    private String hostUrl = null;

    @Value("${inventory.manager.mysql.db.username}")
    private String dbUsername = null;

    @Value("${inventory.manager.mysql.db.password}")
    private String dbPassword = null;

    @Value("${inventory.manager.mysql.db.dialectproperty}")
    private String hibernateDialectProperty = null;

    @Value("${inventory.manager.mysql.db.dialectvalue}")
    private String hibernateDialectValue = null;

    @Value("${inventory.manager.mysql.db.showsqlproperty}")
    private String hibernateShowSqlProperty = null;

    @Value("${inventory.manager.mysql.db.showsqlvalue}")
    private String hibernateShowSqlValue = null;

    @Value("${inventory.manager.mysql.db.packagestoscan}")
    private String packageToScan = null;

    // pooling properties
    @Value("${inventory.manager.database.pooling.acquireincrement}")
    private Integer acquireIncrement;

    @Value("${inventory.manager.database.pooling.maxidletime}")
    private Integer maxIdleTime;

    @Value("${inventory.manager.database.pooling.maxidletimeexcessconnections}")
    private Integer maxIdleTimeExcessConnections;

    @Value("${inventory.manager.database.pooling.maxpoolsize}")
    private Integer maxPoolSize;

    @Value("${inventory.manager.database.pooling.minpoolsize}")
    private Integer minPoolSize;

    @Value("${inventory.manager.database.pooling.numhelperthreads}")
    private Integer numHelperThreads;

    @Value("${inventory.manager.database.pooling.unreturnedconnectiontimeout}")
    private Integer unreturnedConnectionTimeout;

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(databaseDriver);
            dataSource.setJdbcUrl(hostUrl);
            dataSource.setUser(dbUsername);
            dataSource.setPassword(dbPassword);
            dataSource.setMaxIdleTime(maxIdleTime);
            dataSource.setMaxIdleTimeExcessConnections(maxIdleTimeExcessConnections);
            dataSource.setMinPoolSize(minPoolSize);
            dataSource.setMaxPoolSize(maxPoolSize);
            dataSource.setNumHelperThreads(numHelperThreads);
            dataSource.setUnreturnedConnectionTimeout(unreturnedConnectionTimeout);
            dataSource.setAcquireIncrement(acquireIncrement);
            dataSource.setPreferredTestQuery("SELECT 1");
            dataSource.setTestConnectionOnCheckout(false);
            dataSource.setTestConnectionOnCheckin(true);
        } catch (PropertyVetoException e) {
            logger.error("Error occurred while setting up data source", e);
        }
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        entityManagerFactoryBean.setPackagesToScan(packageToScan);
        entityManagerFactoryBean.setJpaProperties(hibProperties());
        return entityManagerFactoryBean;
    }

    private Properties hibProperties() {
        Properties properties = new Properties();
        properties.put(hibernateDialectProperty, hibernateDialectValue);
        properties.put(hibernateShowSqlProperty, hibernateShowSqlValue);
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

}
