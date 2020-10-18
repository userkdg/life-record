package pri.jarod.javaweb.enums;

import pri.jarod.javaweb.common.BaseBmEnum;
import lombok.Getter;

/**
 * 代码类 标志类 文本类 金额类 比例类 数值类 日期类 日期时间类
 *
 * @author kongdegang
 */
public enum StandardDataType implements BaseBmEnum<Integer> {
    代码类(0),
    标志类(1),
    文本类(2),
    金额类(3),
    比例类(4),
    数值类(5),
    日期类(6),
    日期时间类(7),
    时间类(8);
    @Getter
    private Integer code;

    StandardDataType(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
