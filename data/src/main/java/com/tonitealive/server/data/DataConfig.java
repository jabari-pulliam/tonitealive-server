package com.tonitealive.server.data;

import com.tonitealive.server.data.converters.UserProfileModelConverter;
import com.tonitealive.server.data.converters.UserProfileNodeConverter;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootConfiguration
@EnableAutoConfiguration
@EnableNeo4jRepositories
@EnableTransactionManagement
public class DataConfig {

    /**
     * Configures the type conversion service. Converters should be registered here.
     *
     * @return The conversion service
     */
    @Bean
    public ConversionService conversionService() {
        GenericConversionService service = new GenericConversionService();
        service.addConverter(new UserProfileModelConverter());
        service.addConverter(new UserProfileNodeConverter());
        return service;
    }

}
