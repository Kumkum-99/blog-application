package blog.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {
	
	
	  @Bean
	    public OpenAPI blogOpenAPI() {
	        return new OpenAPI()
	                .info(new Info()
	                        .title("Blog API")
	                        .description("API documentation for Blog Application")
	                        
	                        )
	                 .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
	                 .components(new Components().addSecuritySchemes(
	                		 "bearerAuth", new SecurityScheme()
                             .name("Authorization")
                             .type(SecurityScheme.Type.HTTP)
                             .scheme("bearer")
                             .bearerFormat("JWT")
                             .in(SecurityScheme.In.HEADER)
                             .description("Enter JWT token with **Bearer** prefix")));
	                    
	        
	    }
	  
	  
}




