package com.epam.esm.configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@ComponentScan(basePackages = "com.epam.esm")
@EnableWebMvc
public class DatabaseBeanConfig {

    private static final String DRIVER_CLASS_NAME = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost/mjc_module_2?useSSl=false&serverTimezone=Europe/Moscow";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";

    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            dataSource.setDriverClass(DRIVER_CLASS_NAME);
            dataSource.setJdbcUrl(URL);
            dataSource.setUser(USERNAME);
            dataSource.setPassword(PASSWORD);
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }


}
