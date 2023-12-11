package com.myfood.swagger;
import org.springframework.context.annotation.Bean;
/**
 * @author David Maza
 *
 */
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;


@OpenAPIDefinition(info = @Info(title = "API - MyFood 🍜 ", version = "1.0", description = "By Three Elements™"+"( David Maza , Manel Castellví , Daniel Núnez )"))
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer"
)
@Configuration
public class OpenApi30Config {
	
	
    @Bean
    OpenAPI customOpenAPI() {
        return new OpenAPI().addServersItem(new Server().url("http://myfood.up.railway.app/"))
                .addServersItem(new Server().url("https://myfood.up.railway.app/"))
                .addServersItem(new Server().url("http://localhost:8181"));
    }
    
}


