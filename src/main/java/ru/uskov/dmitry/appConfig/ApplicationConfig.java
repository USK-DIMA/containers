package ru.uskov.dmitry.appConfig;

import com.querydsl.sql.SQLServer2012Templates;
import com.querydsl.sql.SQLTemplates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Dmitry on 11.06.2017.
 */
@Configuration
public class ApplicationConfig {

    @Bean
    public SQLTemplates sqlTemplatesFactry() {
        return new SQLServer2012Templates();
    }

}