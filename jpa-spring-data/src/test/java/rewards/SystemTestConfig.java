package rewards;

import config.RewardsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@Import(RewardsConfig.class)
public class SystemTestConfig {
	
	/**
	 * Creates an in-memory "rewards" database populated 
	 * with test data for fast testing
	 */
	@Bean
	public DataSource dataSource(){
		return
			(new EmbeddedDatabaseBuilder())
			.addScript("classpath:rewards/testdb/schema.sql")
			.addScript("classpath:rewards/testdb/data.sql")
			.build();
	}	
	
	
	//	TODO-07: Configure and return a LocalContainerEntityManagerFactoryBean.  Be sure
	//	set the dataSource, jpaVendorAdaptor and other properties appropriately. 
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
		
		// We've set these up for you ...
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabase(Database.HSQL);

		Properties props = new Properties();
		props.setProperty("hibernate.format_sql", "true");

		// Your turn ... configure the emf like the example in the slides ...
		emf.setDataSource(dataSource);
		emf.setPersistenceUnitName("rewardNetwork");
		return emf;
	}

	//	TODO-08: Define a JpaTransactionManager bean with the name transactionManager. 
	//	The @Bean method should accept a parameter of type EntityManagerFactory.
	//	Use this parameter when instantiating the JpaTransactionManager.
	//	Run the RewardNetworkTests, it should pass.
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);
	}
		
}
