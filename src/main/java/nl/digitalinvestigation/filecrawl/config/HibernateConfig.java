package nl.digitalinvestigation.filecrawl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibernateConfig {

    // JDBC values from application.properties
    @Value("${jdbc.driverClassName}")
    private String driverClassName;
    @Value("${jdbc.url}")
    private String dbUrl;
    @Value("${jdbc.username}")
    private String dbUserName;
    @Value("${jdbc.password}")
    private String dbPassword;

    // Hibernate values from application.properties
    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;
    @Value("${hibernate.format_sql}")
    private String hibernateFormatSql;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddl;
    @Value("${hibernate.type}")
    private String hibernateType;
    @Value("${hibernate.use_sql_comments}")
    private String hibernateSqlComments;


    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("nl.digitalinvestigation.filecrawl.model");
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUserName);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", hibernateShowSql);
        properties.put("hibernate.format_sql", hibernateFormatSql);
        properties.put("hibernate.hbm2ddl.auto", hibernateHbm2ddl);
        properties.put("hibernate.trace", hibernateType);
        properties.put("hibernate.use_sql_comments", hibernateSqlComments);
        return properties;
    }
}
