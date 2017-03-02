package com.niit.collaboration.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.niit.collaboration.dao.UserDAO;
import com.niit.collaboration.daoimpl.UserDAOImpl;
import com.niit.collaboration.model.Blog;
import com.niit.collaboration.model.Chat;
import com.niit.collaboration.model.ChatForum;
import com.niit.collaboration.model.ChatForumComment;
import com.niit.collaboration.model.Event;
import com.niit.collaboration.model.Friend;
import com.niit.collaboration.model.Job;
import com.niit.collaboration.model.JobApplication;
import com.niit.collaboration.model.User;

@Configuration
@ComponentScan("com.niit.collaboration")
@EnableTransactionManagement
public class ApplicationContextConfig {
	private static final Logger logger = 
			LoggerFactory.getLogger(ApplicationContextConfig.class);
	
	@Bean(name = "dataSource")
	public DataSource getOracleDataSource() {
		logger.debug("Starting of the method getOracleDataSource");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		
		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		
		dataSource.setUsername("COLB_DB"); //Schema name
		dataSource.setPassword("root");

		Properties connectionProperties = new Properties();
		
		connectionProperties.setProperty("hibernate.dialect",
				   "org.hibernate.dialect.Oracle10gDialect");
		
		
		dataSource.setConnectionProperties(connectionProperties);
		
		logger.debug("Ending of the method getOracleDataSource");
		return dataSource;
	}
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {
		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		//sessionBuilder.addProperties(getHibernateProperties());
		
		//sessionBuilder.scanPackages("com.niit.collaboration.model");
		sessionBuilder.addAnnotatedClass(User.class);

		sessionBuilder.addAnnotatedClass(Blog.class);
		sessionBuilder.addAnnotatedClass(Chat.class);
		sessionBuilder.addAnnotatedClass(Event.class);
		sessionBuilder.addAnnotatedClass(Friend.class);
		sessionBuilder.addAnnotatedClass(Job.class);
		sessionBuilder.addAnnotatedClass(JobApplication.class);
		

		sessionBuilder.addAnnotatedClass(ChatForum.class);
        sessionBuilder.addAnnotatedClass(ChatForumComment.class);
		
		return sessionBuilder.buildSessionFactory();
	}

	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

		return transactionManager;
	}

	@Autowired
	@Bean(name = "userDetailsDAO")
	public UserDAO getUserDetailsDAO(SessionFactory sessionFactory) {
		return new UserDAOImpl(sessionFactory);
	}

	
}
