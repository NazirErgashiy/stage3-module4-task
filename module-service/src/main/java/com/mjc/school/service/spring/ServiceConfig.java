package com.mjc.school.service.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.mjc.school.repository.spring")
@ComponentScan("com.mjc.school.service")
public class ServiceConfig {
}
