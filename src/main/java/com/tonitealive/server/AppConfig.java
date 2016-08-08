package com.tonitealive.server;

import com.tonitealive.server.data.converters.UserProfileEntityConverter;
import com.tonitealive.server.data.converters.UserProfileModelConverter;
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
        service.addConverter(new UserProfileModelConverter());
        service.addConverter(new UserProfileEntityConverter());
        return service;
    }

}
