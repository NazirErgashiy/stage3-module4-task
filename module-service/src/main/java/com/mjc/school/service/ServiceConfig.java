package com.mjc.school.service;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("com.mjc.school.repository")
@ComponentScan("com.mjc.school.service")
public class ServiceConfig {
}
