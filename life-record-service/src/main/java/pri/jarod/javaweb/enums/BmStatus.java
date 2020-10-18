package pri.jarod.javaweb.enums;

import pri.jarod.javaweb.common.BaseBmEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kongdegang
 * @date 2020/7/21 10:57
 */
public enum BmStatus implements BaseBmEnum<Integer> {
    /**
     *
     */
    ON(1, "启用"),
    /**
     *
     */
    DELETE(-1, "删除"),
    DISABLE(0, "禁用");

    @Setter
    @Getter
    private int code;
    @Setter
    @Getter
    private String desc;

    BmStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return getCode();
    }
}
