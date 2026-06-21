package com.quizit.core.global.config

import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories

@Configuration
@EnableRedisRepositories(basePackages = ["com.quizit.core.domain.auth"])
class RedisConfiguration
