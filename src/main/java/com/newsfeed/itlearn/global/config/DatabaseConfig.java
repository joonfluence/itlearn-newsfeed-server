package com.newsfeed.itlearn.global.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;

public class DatabaseConfig {
    public static final String WRITE_HIKARI_CONFIG_PREFIX = "spring.datasource.write";
    public static final String READ_HIKARI_CONFIG_PREFIX = "spring.datasource.read";
    public static final String WRITE_DATASOURCE = "writeDataSource";
    public static final String READ_DATASOURCE = "readDataSource";

    @Bean
    @ConfigurationProperties(prefix = WRITE_HIKARI_CONFIG_PREFIX)
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties(prefix = READ_HIKARI_CONFIG_PREFIX)
    public DataSource readDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    @DependsOn({WRITE_DATASOURCE, READ_DATASOURCE})
    public DataSource routingDataSource(
            @Qualifier(WRITE_DATASOURCE) DataSource writeDataSource,
            @Qualifier(READ_DATASOURCE) DataSource readDataSource
    ) {
        RoutingDataSource routingDataSource = new RoutingDataSource();
        HashMap<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put("write", writeDataSource);
        dataSourceMap.put("read", readDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(writeDataSource);
        return routingDataSource;
    }

    @Bean
    @Primary
    @DependsOn("routingDataSource")
    public LazyConnectionDataSourceProxy dataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
