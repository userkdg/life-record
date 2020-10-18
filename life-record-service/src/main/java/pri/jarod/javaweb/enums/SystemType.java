package pri.jarod.javaweb.enums;

import pri.jarod.javaweb.common.BaseBmEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 系统分类枚举
 *
 * @author kongdegang
 * @date 2020/7/21 13:46
 */
public enum SystemType implements BaseBmEnum<Integer> {
    /**
     *
     */
    BUSINESS_SYS(1, "业务系统"),
    WAREHOUSE_SYS(2, "数仓系统"),
    DATA_PRODUCE(3, "数据产品"),
    OTHER(4, "其他");

    @Setter
    @Getter
    private int code;


    @Setter
    @Getter
    private String name;

    SystemType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return getCode();
    }
}
