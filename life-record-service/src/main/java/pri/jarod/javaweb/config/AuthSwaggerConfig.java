package pri.jarod.javaweb.config;

import pri.jarod.javaweb.constant.DapConstant;
import org.apache.commons.compress.utils.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@Configuration
@EnableSwagger2
//是否开启swagger，正式环境一般是需要关闭的（避免不必要的漏洞暴露！），可根据springboot的多环境配置进行设置
//@ConditionalOnProperty(name = "swagger.enable",  havingValue = "true")
public class AuthSwaggerConfig {

    @Bean
    public Docket createRestApi() {
        List<Parameter> params = Lists.newArrayList();
        //header/query/body..中的token参数非必填，传空也可以
        Parameter ticketPar = new ParameterBuilder()
                .name(DapConstant.WEB_HTTP_TOKEN_NAME).description("user ticket")
                .modelRef(new ModelRef("string")).parameterType("header")
                .required(false).build();
        params.add(ticketPar);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 为当前包路径
                .apis(RequestHandlerSelectors.basePackage("pri.jarod.javaweb.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(params);
    }

    // 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("数据资产平台api接口")
                // 版本号
                .version("1.0")
                // 描述
                .description("数据资产平台后端项目接口详情")
                .build();
    }
}
