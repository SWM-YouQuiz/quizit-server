package com.quizit.api.global.config

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationPropertiesScan(basePackages = ["com.quizit.core"])
@ComponentScan(basePackages = ["com.quizit.api", "com.quizit.core"])
class ScanConfiguration
