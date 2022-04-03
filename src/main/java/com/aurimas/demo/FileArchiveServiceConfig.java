package com.aurimas.demo;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.aurimas.demo.controllers", "com.aurimas.demo.services"})
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class FileArchiveServiceConfig {
}
