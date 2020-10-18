package pri.jarod.javaweb.enums;

import pri.jarod.javaweb.common.BaseBmEnum;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * <code>
 *     1）整型选项  - 当前行数据类型是整型时才显示的选项 (整型类型有tinyint、int、smallint  、mediumint、bigint)
 *
 *         整型类型选项
 *
 *            自动递增：auto_increment
 *
 *            无符号：unsigned
 *
 *            填充零：zerofill
 *
 *   （2）浮点选项 - 当前行数据类型是浮点型时才显示的选项
 *
 *         浮点数据类型选项，如doubule、decimal、float
 *
 *            精度：（5,2）5为精度
 *
 *            标度：（5,2）2为标度
 *
 *   （3）时间选项 - 当前行数据类型是时间类型时才显示的选项，时间类型有：datetime、timestamp、time
 *
 *         复选框代表：ON UPDATE CURRENT_TIMESTAMP （MySQL）
 *
 *         精度：默认精度，不显示毫秒（0-6）
 * </code>
 * @author Jarod.Kong
 * @date 2020/7/22 14:01
 */
public enum DatabaseType implements BaseBmEnum<Integer> {
    /**
     *  整型 tinyint int int32 int64
     *  tinyint、int、smallint  、mediumint、bigint
     */
    MYSQL(1, "mysql"),
    PGSQL(2, "pgsql"),
    HIVE(3, "hive");
    /**
     * 浮点 类型 double decimal float
     */

    /**
     * 日期时间类型 datetime timestamp time
     */

    @Setter
    @Getter
    private int code;
    @Setter
    @Getter
    private String dbName;

    DatabaseType (int code, String dbName) {
        this.code = code;
        this.dbName = dbName;
    }

    @Override
    public Integer getValue() {
        return code;
    }
}
