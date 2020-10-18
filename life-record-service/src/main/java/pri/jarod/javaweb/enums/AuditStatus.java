package pri.jarod.javaweb.enums;

import pri.jarod.javaweb.common.BaseBmEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 稽核状态
 *
 * @author kongdegang
 * @date 2020/7/21 15:12
 */
public enum AuditStatus implements BaseBmEnum<Integer> {
    /**
     *
     */
    AUDIT_WAIT(0, "待稽核", AuditType.SYSTEM),
    AUDIT_DOING(1, "稽核中", AuditType.SYSTEM),
    AUDIT_DONE(2, "已稽核", AuditType.SYSTEM);


    @Setter
    @Getter
    private Integer code;
    @Setter
    @Getter
    private String desc;
    @Getter
    private AuditType type;

    AuditStatus(Integer code, String desc, AuditType type) {
        this.code = code;
        this.desc = desc;
        this.type = type;
    }

    @Override
    public Integer getValue() {
        return getCode();
    }

    enum AuditType {
        SYSTEM(1, "系统类"),
        MODEL(2, "模型类"),
        OTHER(-1, "其他类");
        @Getter
        private final Integer type;
        @Getter
        private final String info;

        AuditType(Integer type, String info) {
            this.type = type;
            this.info = info;
        }
    }


}
