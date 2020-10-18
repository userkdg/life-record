package pri.jarod.javaweb.common.auth;

import com.baomidou.mybatisplus.core.toolkit.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author Jarod.Kong
 * @date 2020/10/16 16:52
 */
@Slf4j
public class Beans {

    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz) {
        T bean = ClassUtils.newInstance(clazz);
        try {
            BeanUtils.populate(bean, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            log.error("map 2 bean ,error", e);
        }
        return bean;
    }
}
