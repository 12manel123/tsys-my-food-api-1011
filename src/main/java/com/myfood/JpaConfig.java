package com.myfood;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;  // Cambiado desde jakarta.activation.DataSource
@Configuration
public class JpaConfig {

    @Bean(name = "jpaSharedEM_entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
        return builder
            .dataSource((javax.sql.DataSource) dataSource)
            .packages("com.myfood")  // Paquete base de tus entidades JPA 
            .persistenceUnit("jpaSharedEM")
            .build();
    }
}