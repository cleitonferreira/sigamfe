package com.sigamfe.configuration;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.mariadb.jdbc.MySQLDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.sigamfe.exception.GenericException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class PersistenceContext.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.sigamfe.repository")
public class PersistenceConfiguration {

	@Getter
	@Setter
	private static String DB_HOSTNAME, DB_PORT;

	private static String DB_USERNAME = "root", DB_PASSWORD = "root", AUTOCONNECTION_TEST_QUERY = "SELECT 1",
			DB_DATABASE = "sigamfe";
	private static boolean AUTO_COMMIT = true;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource datasource;
		try {
			datasource = new MySQLDataSource(DB_HOSTNAME, Integer.parseInt(DB_PORT), DB_DATABASE);
		} catch (NumberFormatException | SQLException e) {
			throw new GenericException("Erro de conexão ao banco de dados. Motivo: " + e.getMessage());
		}
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDataSource(datasource);
		hikariConfig.setUsername(DB_USERNAME);
		hikariConfig.setPassword(DB_PASSWORD);
		hikariConfig.setAutoCommit(AUTO_COMMIT);
		hikariConfig.setConnectionTestQuery(AUTOCONNECTION_TEST_QUERY);

		return new HikariDataSource(hikariConfig);
	}

	private static String PACKAGES_TO_SCAN = "com.sigamfe.model",
			HIBERNATE_DIALECT = "org.hibernate.dialect.MySQL5InnoDBDialect",
			HIBERNATE_EJB_NAMING_STRATEGY = "org.hibernate.cfg.ImprovedNamingStrategy", HIBERNATE_SHOW_SQL = "false",
			HIBERNATE_FORMAT_SQL = "true";
	// , HIBERNATE_HBM2DDL_AUTO = "validate";

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan(PACKAGES_TO_SCAN);
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", HIBERNATE_DIALECT);
		properties.setProperty("hibernate.ejb.naming_strategy", HIBERNATE_EJB_NAMING_STRATEGY);
		properties.setProperty("hibernate.show_sql", HIBERNATE_SHOW_SQL);
		properties.setProperty("hibernate.format_sql", HIBERNATE_FORMAT_SQL);
		properties.setProperty("hibernate.hbm2ddl.auto", "update");
		entityManagerFactoryBean.setJpaProperties(properties);

		return entityManagerFactoryBean;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslator() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}
