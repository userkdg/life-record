package pri.jarod.javaweb.common;

import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;
import java.util.stream.Stream;

/**
 * @author Jarod.Kong
 * @date 2020/8/3 10:19
 */
public interface BaseBmEnum<T extends Serializable> extends IEnum<T>, Serializable {
    /**
     * 根据enumClass & value 获取枚举值
     * <p>
     * eg:
     * GoodsStatus of = BaseEnum.of(GoodsStatus.class, 1);
     *
     * @param enumClass E extends Enum<?> & IEnum 集成了IEnum的枚举，并赋值给抽象接口getValue的可序列化值
     * @param value     可序列化值
     * @param <E>       实现类
     * @return 枚举类
     */
    static <E extends Enum<?> & IEnum<E>> E of(Class<E> enumClass, Serializable value) {
        return Stream.of(enumClass.getEnumConstants())
                .filter((e) -> e.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("通过 " + enumClass.getName() + "类，找不到 Enum 对应的Value '" + value + "' "));
    }

    static <E extends Enum<?> & IEnum<E>> E valueOrNull(Class<E> enumClass, Serializable value) {
        return Stream.of(enumClass.getEnumConstants())
                .filter((e) -> e.getValue().equals(value))
                .findFirst()
                .orElse(null);
    }
}
