package com.syy.hardware.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @ClassName SwaggerConfig
 * @Description TODO
 * @Author Administrator
 * @Data 2019/4/1 20:21
 * @Version 1.0
 **/
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.syy.hardware.controller")) // 配置需要扫描的包路径
                .paths(PathSelectors.any())
                .build();
        //.globalOperationParameters(pars);
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("身份认证服务")
                .description("提供统一的身份认证与身份验证服务")
                .termsOfServiceUrl("http://172.16.0.145:8011")
                .version("0.0.1")
                .build();
    }
}
