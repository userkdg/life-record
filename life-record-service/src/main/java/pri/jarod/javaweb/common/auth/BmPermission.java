package pri.jarod.javaweb.common.auth;

import java.lang.annotation.*;

/**
 * @author Jarod.Kong
 * @date 2020/10/16 15:22
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BmPermission {
    /**
     * 权限标记符
     *
     * @return 权限标记符
     */
    String[] value();

    /**
     * @return 名称描述
     */
    String name() default "";

    /**
     * 定义该注解是否有效
     * true为忽略，不进行权限校验
     * false为默认，进行权限校验
     *
     * @return f
     */
    boolean ignore() default false;
}
