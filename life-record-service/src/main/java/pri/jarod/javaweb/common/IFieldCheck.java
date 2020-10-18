package pri.jarod.javaweb.common;

/**
 * @author Jarod.Kong
 * @date 2020/8/11 14:35
 */
public interface IFieldCheck<T> {
    /**
     * 检查 t
     *
     * @param t
     * @return
     */
    boolean check(T t);
}
