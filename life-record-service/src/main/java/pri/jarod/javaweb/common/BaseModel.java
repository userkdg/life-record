package pri.jarod.javaweb.common;

import pri.jarod.javaweb.enums.BmStatus;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * model基类
 *
 * @author Jarod.Kong
 * @date 2020/8/3 11:10
 */
@Data
public abstract class BaseModel<T extends Model<?>> extends Model<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @SuppressWarnings({"unchecked"})
    @ApiModelProperty(hidden = true)
    public static <T extends Model<?>> BaseModel<T> empty(){
        //noinspection rawtypes
        return new BaseModelEmpty();
    }

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建人")
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "修改人")
    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "最后更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "表数据状态: -1:删除 0:禁用 1:启用")
    @EnumValue
    private BmStatus bmStatus = BmStatus.ON;

    /**
     * 增加空对象
     */
    private static class BaseModelEmpty<T extends Model<T>> extends BaseModel<T> {
        private <T>BaseModelEmpty(){}
    }
}
