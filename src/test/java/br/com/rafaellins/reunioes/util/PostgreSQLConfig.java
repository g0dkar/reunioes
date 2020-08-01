package br.com.rafaellins.reunioes.util;

import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.annotation.PreDestroy;

@Configuration
public class PostgreSQLConfig {
    private static final String POSTGRESQL_IMAGE = "postgres:12-alpine";

    private PostgreSQLContainer<?> postgresql;

    public PostgreSQLConfig() {
        postgresql = new PostgreSQLContainer<>(POSTGRESQL_IMAGE);
        postgresql.start();

        System.setProperty("DB_URL", postgresql.getJdbcUrl());
        System.setProperty("DB_USERNAME", postgresql.getUsername());
        System.setProperty("DB_PASSWORD", postgresql.getPassword());
    }

    @PreDestroy
    public void preDestroy() {
        postgresql.stop();
    }
}
