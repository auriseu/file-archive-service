package com.aurimas.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "com.aurimas.demo.statistics.dao")
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class H2JpaConfig {

}