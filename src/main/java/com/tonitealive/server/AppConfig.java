package com.tonitealive.server;

import com.tonitealive.server.converters.UserProfileConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.GenericConversionService;

@Configuration
public class AppConfig {

    /**
     * Configures the type conversion service. Converters should be registered here.
     *
     * @return The conversion service
     */
    @Bean
    public ConversionService conversionService() {
        GenericConversionService service = new GenericConversionService();
        service.addConverter(new UserProfileConverters.ModelConverter());
        service.addConverter(new UserProfileConverters.EntityConverter());
        return service;
    }

}
