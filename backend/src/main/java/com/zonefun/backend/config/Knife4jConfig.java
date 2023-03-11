package com.zonefun.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName Knife4jConfig
 * @Description knife4j配置
 * @Date 2022/11/15 10:05
 * @Author ZoneFang
 */
@Configuration
@EnableSwagger2
public class Knife4jConfig {

    /**
     * swagger配置
     *
     * @return springfox.documentation.spring.web.plugins.Docket
     * @date 2022/11/15 10:12
     * @author ZoneFang
     */
    @Bean
    public Docket swagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 一些自定义形式
                .apiInfo(apiInfoBuilder())
                .select()
                // Api包
                .apis(RequestHandlerSelectors.basePackage("com.zonefun.backend.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * ApiInfo创建
     *
     * @return springfox.documentation.service.ApiInfo
     * @date 2022/11/15 10:19
     * @author ZoneFang
     */
    private ApiInfo apiInfoBuilder() {
        return new ApiInfoBuilder()
                .title("Zone's Algorithm API")
                .contact(new Contact("ZoneFang", "@zonefang88", "2628347005@qq.com"))
                .description("集中的算法云服务")
                .version("1.0.0")
                .build();
    }
}
