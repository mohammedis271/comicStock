package za.co.entelect.superman.superman;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("za.co.entelect.superman.superman.api"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Comic Stock  API",
                "This Api is responsible to Connecting to clients that handle customer Comic stocks operation.",
                "API 1.0",
                "Uses a JWT auth token",
                new Contact("Team SuperMan", "TeamSuperman.entelect.co.za", "TeamSuperMan@Entelect.co.za"),
                "Entelect Solution", "API URL", Collections.emptyList());
    }
}