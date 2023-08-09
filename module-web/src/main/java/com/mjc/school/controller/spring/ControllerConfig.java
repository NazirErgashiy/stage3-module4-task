package com.mjc.school.controller.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.mjc.school.service.spring")
@ComponentScan("com.mjc.school.controller")
public class ControllerConfig {
}
