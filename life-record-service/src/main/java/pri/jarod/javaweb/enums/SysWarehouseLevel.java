package pri.jarod.javaweb.enums;

import pri.jarod.javaweb.common.BaseBmEnum;
import lombok.Getter;

/**
 * OD、DWD、DIM、DWS、ADS
 *
 *
 * 当前模型为数仓模出现该选项，并且必选
 *
 * @author kongdegang
 * @date 2020/7/21 10:57
 */
@Getter
public enum SysWarehouseLevel implements BaseBmEnum<Integer> {
    /**
     * OD、DWD、DIM、DWS、ADS
     */
    ODS(1, "ODS", SystemType.WAREHOUSE_SYS),
    DWD(2, "DWD", SystemType.WAREHOUSE_SYS),
    DIM(3, "DIM", SystemType.WAREHOUSE_SYS),
    DWS(4, "DWS", SystemType.WAREHOUSE_SYS),
    ADS(5, "ADS", SystemType.WAREHOUSE_SYS);

    private final int code;

    private final String desc;

    private final SystemType warehouseSys;

    SysWarehouseLevel(int code, String desc, SystemType warehouseSys) {
        this.code = code;
        this.desc = desc;
        this.warehouseSys = warehouseSys;
    }

    @Override
    public Integer getValue() {
        return getCode();
    }
}
