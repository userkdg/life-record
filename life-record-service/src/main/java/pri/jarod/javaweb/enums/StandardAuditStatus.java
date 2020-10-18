package pri.jarod.javaweb.enums;

import pri.jarod.javaweb.common.BaseBmEnum;
import lombok.Getter;

/**
 * 审核：草稿，待审批，不通过，已发布
 * @since 2020年10月15日20:10:06 调整类型排序，不通过和待审批互换
 *  影响：前端翻译，后端数据库
 *
 * @author Administrator
 */
public enum StandardAuditStatus implements BaseBmEnum<Integer> {
    草稿(0),
    不通过(1),
    待审批(2),
//    不通过(2),
    已发布(3),
    撤销(4),
    通过(5),
    新版本(6);
    @Getter
    private Integer code;

    StandardAuditStatus(Integer code) {
        this.code = code;
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
