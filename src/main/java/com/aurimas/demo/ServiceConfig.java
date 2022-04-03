package com.aurimas.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.aurimas.demo.controllers"})
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class ServiceConfig {
}
