package pri.jarod.javaweb.common;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 针对 T的类进行分页定义
 * use
 * <code>
 *         BmPageRequestVo<DapStandardAuditVersionMain> of = BmPageRequestVo.of(1, 10, null);
 *         Page<DapStandardAuditVersionMain> page = of.toMpPage();
 *         IPage<DapStandardAuditVersionMain> pageResult = new DapStandardAuditVersionMain().selectPage(page, Wrappers.emptyWrapper());
 *         System.out.println(pageResult);
 * </code>
 * @author Administrator
 */
@Data
@NoArgsConstructor
public class BmPageRequestVo<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(hidden = true)
    private final Page<T> mpPage = new Page<>();
    /**
     * 第几页 对应 antdv的分页属性 current
     */
    @ApiModelProperty(value = "请求当前页码，默认为1", example = "1")
    private long current = mpPage.getCurrent();
    /**
     * 每页显示几条内容 对应 antdv的分页属性 pageSize
     */
    @ApiModelProperty(value = "请求当前页数量，默认为10", example = "10")
    private long pageSize = mpPage.getSize();
    /**
     * 针对查询条件进行排序
     * 在分页中增加排序方式，无效额外设置
     */
    @ApiModelProperty(hidden = true)
    private List<OrderItem> orders = new ArrayList<>();

    public BmPageRequestVo(long currentPage, long size, List<OrderItem> orders) {
        this.current = currentPage;
        this.pageSize = size;
        this.orders = orders;
    }

    public static <T> BmPageRequestVo<T> of(long currentPage, long size, @Nullable List<OrderItem> orders) {
        return new BmPageRequestVo<>(currentPage, size, orders);
    }

    /**
     * 转为mp的分页请求
     *
     * @return
     */
    public Page<T> toMpPage() {
        mpPage.setCurrent(getCurrent());
        mpPage.setSize(getPageSize());
        mpPage.setOrders(getOrders());
        return mpPage;
    }

}
