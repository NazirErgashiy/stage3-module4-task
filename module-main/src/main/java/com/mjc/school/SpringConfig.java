package com.mjc.school;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.mjc.school.controller.implementation")
@ComponentScan("com.mjc.school.repository.implementation.dao")
@ComponentScan("com.mjc.school.repository.implementation")
@ComponentScan("com.mjc.school.repository")
@ComponentScan("com.mjc.school.service")
public class SpringConfig {
}
