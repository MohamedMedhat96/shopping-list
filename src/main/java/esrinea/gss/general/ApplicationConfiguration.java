package esrinea.gss.general;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

//Configuration Class to configure the database session factory and it's configurations as a bean.
@Configuration
public class ApplicationConfiguration {
	@Value("${spring.datasource.url}")
    private String jdbcURl;

@Value("${spring.datasource.username}")
    private String dbUsername;

@Value("${spring.datasource.password}")
    private String dbPassword;

//Creating the session factory bean
	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource()); // Adding the data source properties.
		sessionFactory.setPackagesToScan(new String[] { "esrinea.gss.shopping.category", "esrinea.gss.shopping.item",
				"esrinea.gss.shopping.operation", "esrinea.gss.shopping.report" }); // base packages to be scanned
		sessionFactory.setHibernateProperties(hibernateProperties()); // Hibernate properties and configurations

		return sessionFactory;
	}
	
	//returns the database properties
	@Bean
	public DataSource dataSource() {
		// TODO Done! Configure the DB URL, credentials, etc... as entries in .properties file
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl(this.jdbcURl);
		dataSource.setUsername(this.dbUsername);
		dataSource.setPassword(this.dbPassword);

		return dataSource;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {

		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	//Hibernate Properties
	Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.ddl-auto", "update");
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
		properties.setProperty("hibernate.connection.pool_size", "10");
		properties.setProperty("hibernate.show_sql", "true");
		
		return properties;
	}
}
