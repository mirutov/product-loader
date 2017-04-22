package com.tsum.etl.manager.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by aam on 12.03.17.
 */
@Configuration
@ComponentScan(basePackages={"com.tsum.etl.manager.config"})
@EnableJpaRepositories("com.tsum.etl.tsumwriter.repository")
@PropertySource("application.properties")
@EnableTransactionManagement
public class TSUMTestJPAConfig {
    private static final Logger logger = LoggerFactory.getLogger(TSUMTestJPAConfig.class);
    @Autowired
    private Environment env;

    @Bean("entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setPackagesToScan(new String[] { "com.tsum.etl.tsumwriter.entity" });
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setDataSource(initDataSource());
        em.setJpaProperties(initAdditionalProperties());
        em.afterPropertiesSet();
        em.setDataSource(dataSource());
        em.setJpaProperties(additionalProperties());
        em.afterPropertiesSet();
        return em;
    }

    public DataSource initDataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setDriverClassName(env.getProperty("tsumwriter-test.datasource.driver-class-name"));
        ds.setUrl(env.getProperty("tsumwriter-test.init-datasource.url"));
        ds.setUsername(env.getProperty("tsumwriter-test.datasource.username"));
        ds.setPassword(env.getProperty("tsumwriter-test.datasource.password"));
        return ds;
    }

    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();

        ds.setDriverClassName(env.getProperty("tsumwriter-test.datasource.driver-class-name"));
        ds.setUrl(env.getProperty("tsumwriter-test.datasource.url"));
        ds.setUsername(env.getProperty("tsumwriter-test.datasource.username"));
        ds.setPassword(env.getProperty("tsumwriter-test.datasource.password"));
        return ds;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation(){
        return new PersistenceExceptionTranslationPostProcessor();
    }


    Properties initAdditionalProperties() {
        Properties properties = new Properties();

        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        properties.setProperty("javax.persistence.schema-generation.database.action", "drop-and-create");
        properties.setProperty("javax.persistence.schema-generation.create-source", "script");
        properties.setProperty("javax.persistence.schema-generation.create-script-source", "create.sql");
        properties.setProperty("javax.persistence.schema-generation.drop-source", "script");
        properties.setProperty("javax.persistence.schema-generation.drop-script-source", "drop.sql");
        properties.setProperty("javax.persistence.sql-load-script-source", "data.sql");
        properties.setProperty("hibernate.hbm2ddl.import_files_sql_extractor",
                "org.hibernate.tool.hbm2ddl.MultipleLinesSqlCommandExtractor");

        return properties;
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}