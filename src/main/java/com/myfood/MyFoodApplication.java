package com.myfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.myfood.dao", entityManagerFactoryRef = "jpaSharedEM_entityManagerFactory")
public class MyFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFoodApplication.class, args);
	}
	

}
