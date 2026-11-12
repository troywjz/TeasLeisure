package com.zbkj.front.config;

import com.zbkj.common.constants.Constants;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/** Springdoc OpenAPI groups for CRMEB front-end and public endpoints. */
@Configuration
@ConditionalOnProperty(prefix = "api.doc", name = "swagger-enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi frontOpenApi() {
        return GroupedOpenApi.builder()
                .group("front")
                .packagesToScan("com.zbkj.front")
                .pathsToMatch("/api/front/**")
                .build();
    }

    @Bean
    public GroupedOpenApi publicOpenApi() {
        return GroupedOpenApi.builder()
                .group("public")
                .packagesToScan("com.zbkj.front")
                .pathsToMatch("/api/public/**")
                .build();
    }

    @Bean
    public OpenAPI crmebFrontOpenApi(@Value("${crmeb.domain:}") String domain) {
        OpenAPI openApi = new OpenAPI()
                .info(new Info().title("Crmeb Java").description("Crmeb").version("1.0.0"))
                .components(new Components().addSecuritySchemes(Constants.HEADER_AUTHORIZATION_KEY,
                        new SecurityScheme().type(SecurityScheme.Type.APIKEY)
                                .in(SecurityScheme.In.HEADER)
                                .name(Constants.HEADER_AUTHORIZATION_KEY)
                                .description("CRMEB Authorization token; include the Bearer prefix when applicable.")))
                .addSecurityItem(new SecurityRequirement().addList(Constants.HEADER_AUTHORIZATION_KEY));
        if (StringUtils.hasText(domain) && (domain.startsWith("http://") || domain.startsWith("https://"))) {
            openApi.addServersItem(new Server().url(domain));
        }
        return openApi;
    }
}
