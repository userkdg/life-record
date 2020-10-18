package pri.jarod.javaweb.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * @author Jarod.Kong
 * @date 2020/8/24 11:17
 */
@Configuration
public class CacheConfig {

    @Bean
    public KeyGenerator dapKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(Optional.ofNullable(obj).orElse("").toString());
            }
            return sb.toString();
        };

    }
}