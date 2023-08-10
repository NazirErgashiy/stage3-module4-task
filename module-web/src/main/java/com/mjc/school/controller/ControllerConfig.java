package com.mjc.school.controller;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("com.mjc.school.service")
@ComponentScan("com.mjc.school.controller")
public class ControllerConfig {
}
