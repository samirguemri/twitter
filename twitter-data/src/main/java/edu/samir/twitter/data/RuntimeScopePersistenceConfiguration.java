package edu.samir.twitter.data;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

/**
 *
 * @author Samir
 */
@Configuration
@ComponentScan(basePackages = { "edu.samir.twitter.data" })
@PropertySource({"applicationConfiguration.properties"})
@EnableTransactionManagement
public class RuntimeScopePersistenceConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(RuntimeScopePersistenceConfiguration.class);

    @Value("${jdbc.dataSourceClassName}")
    private String dataSourceClassName;

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String jdbcUrl;

    @Value("${jdbc.username}")
    private String jdbcUser;

    @Value("${jdbc.password}")
    private String jdbcPassword;

    @Value("${hibernate.dialect}")
    private String hibernateDialect;

    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddlAuto;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LOGGER.debug("PersistenceConfiguration.sessionFactory()");
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(packagesToScan());
        sessionFactory.setHibernateProperties(additionalProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        return actualDataSource();
    }

    public DataSource actualDataSource() {
        LOGGER.debug("PersistenceConfiguration.dataSource()");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("org.h2.Driver");
        dataSource.setUrl("jdbc:h2:mem:twitter;DB_CLOSE_DELAY=-1");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        //return dataSource;
        return new HikariDataSource(HikariConfiguration());
    }

    private HikariConfig HikariConfiguration() {

        LOGGER.debug("PersistenceConfiguration.properties()");

        Context ctx;
        DataSource dataSource = null;
        try {
            ctx = new InitialContext();
            dataSource = (DataSource) ctx.lookup("java:/TwitterH2DS");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        HikariConfig config = new HikariConfig();
        config.setDataSource(dataSource);
        config.setMaximumPoolSize(3);
        return config;
    }

    private Properties additionalProperties() {
        Properties additionalProperties = new Properties();
        additionalProperties.setProperty( "hibernate.dialect", hibernateDialect);
        additionalProperties.setProperty( "hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        return additionalProperties;
    }

    private String[] packagesToScan() {
        return new String[]{
                "edu.samir.twitter.model"
        };
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}

