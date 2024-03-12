package com.real.estate.priceservice.config;

import io.r2dbc.spi.ConnectionFactory;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.connection.init.CompositeDatabasePopulator;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EnableScheduling
class PriceServiceConfiguration extends AbstractR2dbcConfiguration {

    @Value("${external-api.offer-service-host}")
    private String offerServiceHost;

//    private final ConnectionFactory connectionFactory;
//
//    PriceServiceConfiguration(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
//        this.connectionFactory = connectionFactory;
//    }

//    @Bean
//    public ConnectionFactory connectionFactory(){
//        return ConnectionFactoryBuilder.get(
//                ConnectionFactoryOptions
//                        .parse("r2dbc:h2:mem:///estate")
//                        .mutate()
//                        .option(ConnectionFactoryOptions.USER, "user")
//                        .option(ConnectionFactoryOptions.PASSWORD, "password")
//                        .build());
//    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return ConnectionFactoryBuilder.withUrl("r2dbc:h2:mem:///estate")
                .username("user")
                .password("password")
                .build();
//        return new H2ConnectionFactory(
//                H2ConnectionConfiguration.builder()
//                        .url("mem:estate")
//                        .username("user")
//                        .password("password")
//                        .build()
//        );
    }

    @Bean
    public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);

        var populator = new CompositeDatabasePopulator();
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));
        populator.addPopulators(new ResourceDatabasePopulator(new ClassPathResource("data.sql")));
        initializer.setDatabasePopulator(populator);

        return initializer;
    }

    @Bean
    public ReactiveTransactionManager transactionManager(ConnectionFactory connectionFactory) {
        return new R2dbcTransactionManager(connectionFactory);
    }

    @Bean
    public DSLContext jooqConfig(ConnectionFactory connectionFactory){
        return DSL.using(connectionFactory).dsl();
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.baseUrl(offerServiceHost).build();
    }
}
