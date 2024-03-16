package com.example.server.configurator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class Configurator {
    /**
     *  Environment variable.
     */
    private final Environment env;
    /**
     *  Constructor.
     * @param environment environment.
     */
    @Autowired
    public Configurator(final Environment environment) {
        this.env = environment;
    }
    /**
     *  Data source.
     * @return data source.
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite:./enroll.sqlite");
        dataSource.setUsername("admin");
        dataSource.setPassword("admin");
        return dataSource;
    }
}
