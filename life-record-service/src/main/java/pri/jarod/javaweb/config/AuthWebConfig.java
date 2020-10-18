package pri.jarod.javaweb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;
import pri.jarod.javaweb.common.auth.UserInfoHolder;
import pri.jarod.javaweb.constant.DapConstant;
import pri.jarod.javaweb.exception.DapThrowException;
import pri.jarod.javaweb.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static pri.jarod.javaweb.common.ResultBean.FORBIDDEN;

/**
 * @author Jarod.Kong
 * @date 2020/8/18 11:09
 */
@Configuration
@Slf4j
public class AuthWebConfig implements WebMvcConfigurer {
    @Autowired
    private UserService userService;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE")
                .allowedOrigins("*")
                .exposedHeaders("Set-Cookie")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**
     * 排除url 调试，
     * {@link MappedInterceptor#matches(String, org.springframework.util.PathMatcher)}
     * 1.token流程
     * 蓝数通登录入口->走统一网关（生成token)->配置项目信息（跳转到项目<em>【前端】</em>的请求url）
     * ->跳转到对应项目前端url，<em>附带网关生成的token信息</em> -> 前端存储localStorage ->后续每个请求项目接口都需要
     * 在url带上token=xxx参数 -> 后端通过拦截到的token进行用户信息、角色进行放权
     * <p>
     * 问题：The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
     * <code>
     * response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader("origin"));
     * response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"*");
     * response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"*");
     * response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS,"true");
     * response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE,"3600");
     * String token = request.getHeader(DapConstant.WEB_HTTP_TOKEN_NAME);
     * </code>
     *
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptorAdapter() {
            /**
             * 1.token无效，http 401, 跳转到登录页面
             * 2.token有效，会刷新有效时间
             * 2.1.没权限，http 403
             * 2.2.放权,http 200/500
             */
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                //检测是options方法则直接返回200(否则报跨越问题)
                if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
                    response.setStatus(HttpStatus.OK.value());
                    return true;
                }
                String token = request.getHeader(DapConstant.WEB_HTTP_TOKEN_NAME);
                // 获取用户，若用户有效则会刷新token有效时间
                String userAccount = userService.isLoginUser(token);
                if (token == null || userAccount == null) {
                    // 目前不对该参数进行权限控制，后续待用户与角色明确后，进行放权控制
                    log.error("请求参数中不存在token信息，不放行[目前待明确需求才进行控制]");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    throw new DapThrowException(FORBIDDEN.getMsg(), FORBIDDEN.getCode());
                }
                // 放行，获取用户信息
                UserInfoHolder.UserInfo userInfoById = userService.getUserInfoByUserId(userAccount);
                UserInfoHolder.setUserInfo(userInfoById);
                return true;
            }

            /**
             * This implementation is empty.
             *
             * @param request
             * @param response
             * @param handler
             * @param ex
             */
            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
                super.afterCompletion(request, response, handler, ex);
                UserInfoHolder.clearUserInfo();
            }
        }).addPathPatterns("/**")
                .excludePathPatterns()
                .excludePathPatterns(
                        "/error",
                        "/login",
                        "/swagger-resources/**",
                        "/v2/**",
                        "/swagger-ui.html",
                        "/swagger-resources/**",
                        "/webjars/springfox-swagger-ui/**",
                        "/csrf")
                .order(Integer.MAX_VALUE);
    }


}
