package com.wsd.ecommerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.wsd.ecommerce.constant.SwaggerConstant.*;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI config(@Value("${server.servlet.context-path:}") String contextPath) {
        return new OpenAPI()
                .info(new Info()
                        .title(API_TITLE)
                        .version(API_VERSION)
                        .description(API_DESCRIPTION))
                .servers(List.of(
                        new Server().url(contextPath.isEmpty() ? "/" : contextPath)));
    }
}
