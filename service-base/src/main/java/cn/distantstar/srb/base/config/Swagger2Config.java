package cn.distantstar.srb.base.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: distantstar
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket adminApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();
    }

    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/api/core/.*")))
                .build();
    }

    @Bean
    public Docket apiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("api")
                .apiInfo(apiInfo())
                .select()
                //只显示admin路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/api/sms/.*")))
                .build();

    }

    @Bean
    public Docket apiOssConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("apiOss")
                .apiInfo(apiOssInfo())
                .select()
                //只显示admin路径下的页面
                .paths(Predicates.and(PathSelectors.regex("/api/oss/.*")))
                .build();

    }

    private ApiInfo adminApiInfo() {
        return new ApiInfoBuilder()
                .title("尚融宝后台管理系统-API文档")
                .description("本文档描述了尚融宝后台管理系统接口")
                .version("1.0")
                .contact(new Contact("Star", "http://distantstar.cn", "1261276386@qq.com"))
                .build();
    }

    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("尚融宝网站-API文档")
                .description("本文档描述了尚融宝网站接口")
                .version("1.0")
                .contact(new Contact("Star", "http://distantstar.cn", "1261276386@qq.com"))
                .build();
    }

    private ApiInfo apiInfo(){

        return new ApiInfoBuilder()
                .title("尚融宝-API文档")
                .description("本文档描述了尚融宝接口")
                .version("1.0")
                .contact(new Contact("Star", "http://distantstar.cn", "1261276386@qq.com"))
                .build();
    }

    private ApiInfo apiOssInfo(){

        return new ApiInfoBuilder()
                .title("尚融宝-API文档")
                .description("本文档描述了尚融宝Oss接口")
                .version("1.0")
                .contact(new Contact("Star", "http://distantstar.cn", "1261276386@qq.com"))
                .build();
    }
}
