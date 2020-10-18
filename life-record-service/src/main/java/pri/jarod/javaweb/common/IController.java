package pri.jarod.javaweb.common;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Jarod.Kong
 * @date 2020/8/14 15:24
 */
@FunctionalInterface
public interface IController<E> {
    /**
     * @return 获取当前控制器的业务类
     */
    IService<E> getService();

}
