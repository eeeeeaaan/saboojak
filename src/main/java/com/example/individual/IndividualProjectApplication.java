package com.example.individual;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableJpaRepositories(
	basePackages = "com.example.individual.domain" // domain 하위의 모든 jpaRepository 스캔
)
@EnableRedisRepositories(
	basePackages = "com.example.individual.infra.redis" // infra 하위의 redisRepository만 스캔
)
public class IndividualProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndividualProjectApplication.class, args);
	}

}
