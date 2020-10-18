package pri.jarod.javaweb.enums;

import pri.jarod.javaweb.constant.DapConstant;

/**
 * @author Jarod.Kong
 * @date 2020/9/27 8:43
 */
public enum AuditCheckType {
    CHINESE(1, DapConstant.AUDIT_SYSTEM_REPORT_CHINESE, "表&字段结构完整性检查", "检查表级字段的中文名是否存在"),
    STANDARD(2, DapConstant.AUDIT_SYSTEM_REPORT_STANDARD, "标准符合度检查", "检查已映射标准的字段，长度及类型是否与对应的标准相符");

    private final int code;
    private final String springBeanName;
    private final String checkType;
    private final String checkDesc;

    AuditCheckType(int code, String springBeanName, String checkType, String checkDesc) {
        this.code = code;
        this.springBeanName = springBeanName;
        this.checkType = checkType;
        this.checkDesc = checkDesc;
    }

    public int getCode() {
        return code;
    }

    public String getCheckType() {
        return checkType;
    }

    public String getCheckDesc() {
        return checkDesc;
    }

    public String getSpringBeanName() {
        return springBeanName;
    }
}

