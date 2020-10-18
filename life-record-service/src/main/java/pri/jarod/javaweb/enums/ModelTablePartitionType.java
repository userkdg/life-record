package pri.jarod.javaweb.enums;

import pri.jarod.javaweb.common.BaseBmEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 对应的分区类型：0:range 1:list 2:hash 3:key
 *
 * @author Jarod.Kong
 * @date 2020/7/22 13:18
 */
public enum ModelTablePartitionType implements BaseBmEnum<Integer> {
    RANGE(0, DatabaseType.MYSQL),
    LIST(1, DatabaseType.MYSQL),
    HASH(2, DatabaseType.MYSQL),
    KEY(3, DatabaseType.MYSQL),
    PG_RANGE(4, DatabaseType.PGSQL),
    PG_LIST(1, DatabaseType.PGSQL),
    PG_HASH(2, DatabaseType.PGSQL),
    PG_KEY(3, DatabaseType.PGSQL);

    @Getter
    @Setter
    private Integer code;
    @Setter
    @Getter
    private DatabaseType databaseType;

    ModelTablePartitionType(Integer code, DatabaseType databaseType) {
        this.code = code;
        this.databaseType = databaseType;
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
