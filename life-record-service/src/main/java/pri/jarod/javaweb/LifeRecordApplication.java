package pri.jarod.javaweb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@MapperScan("pri.jarod.javaweb.mapper")
@SpringBootApplication
@EnableSwagger2
@EnableCaching
public class LifeRecordApplication {

	public static void main(String[] args) {
        SpringApplication.run(LifeRecordApplication.class, args);
	}

}