package pri.jarod.javaweb.enums;

import pri.jarod.javaweb.common.BaseBmEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author kongdegang
 * @date 2020/7/21 10:57
 */
public enum ReferRulesType implements BaseBmEnum<Integer> {
    /**
     *
     */
    COUNTRY_STD(1, "国家标准"),
    /**
     *
     */
    PROFESSION_STD(2, "行业标准"),
    COMPANY_STD(3, "内部标准"),
    OTHER_STD(4, "其他标准");

    @Setter
    @Getter
    private int code;
    @Setter
    @Getter
    private String desc;

    ReferRulesType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return getCode();
    }
}
