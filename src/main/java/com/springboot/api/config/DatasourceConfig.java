package com.springboot.api.config;

/**
 * Created by VDE on 10 Sept 2018.
 */
/*@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.springboot.api.repository")
public class DatasourceConfig {

//    @Bean
//    public DataSource datasource() throws PropertyVetoException {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        EmbeddedDatabase dataSource = builder
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript("sql-scripts/schema.sql")
//                .addScript("sql-scripts/data.sql")
//                .build();
//
//        return dataSource;
//    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("datasource") DataSource ds) throws PropertyVetoException{
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(ds);
        entityManagerFactory.setPackagesToScan(new String[]{"com.springboot.api.domain"});
        JpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactory.setJpaVendorAdapter(jpaVendorAdapter);
        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}*/