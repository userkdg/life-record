package pri.jarod.javaweb.common;

import pri.jarod.javaweb.enums.BmStatus;
import pri.jarod.javaweb.util.BmAssetUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 针对基于mp 的服务层，进行抽象封装
 * <p>1.增加{@link IBmService#getBaseMapper(java.lang.Class)}返回对应的Mapper类
 * <p>2.重写常用查询方法
 * <p> {@link IBmService#getByIdAsset(java.io.Serializable)}查询方法进行断言判断
 * <p> {@link IBmService#getByIdAsset(Serializable, String)}查询方法进行断言判断,指定异常信息
 * <p> {@link IBmService#removeByIdsAsset(boolean, Serializable...)}根据id进行删除的，进行数据存在校验+批量删除+逻辑、物理删除
 * <p> {@link IBmService#toAntDvPage(IPage)} & {@link IBmService#toAntDvPage(IPage, String, Object)}转为antdv组件默认分页属性
 * <p> {@link IBmService#bmLambdaQuery()} & {@link IBmService#bmQuery()} 增加默认取有效的查询条件，减少重复代码
 * <p> {@link IBmService#bmLambdaUpdate()} ()} & {@link IBmService#bmUpdate()} ()} 增加默认取有效的更新条件，减少重复代码
 * <p> {@link IBmService#logicPage(java.util.List, long, long)} &  {@link IBmService#bmLogicPage(java.util.List, long, long)} 增加逻辑分页方法
 *
 * @author Jarod.Kong
 * @date 2020/8/11 16:24
 */
public interface IBmService<T extends BaseModel<T>> extends IService<T> {

    /**
     * 转为antdv 组件的分页参数类
     *
     * @param page mp分页接口
     * @return 转为antdv 组件的分页参数类
     */
    static ResultBean<?> toAntDvPageTool(IPage<?> page) {
        return ResultBean.page(page).toAntDvPage().orElseEmptyPage();
    }

    /**
     * 基于全数据进行逻辑分页，返回
     *
     * @param list     全数据
     * @param current  当前页
     * @param pageSize 页数
     * @param <VO>     vo
     * @return {@link IPage}
     */
    static <VO> IPage<VO> logicPage(List<VO> list, long current, long pageSize) {
        Page<VO> page = new Page<>();
        if (list == null || list.isEmpty()) {
            return page;
        }
        int total = list.size();
        if (current <= 0) current = 1;
        long skip = ((current - 1) * pageSize) > total ? total : ((current - 1) * pageSize);
        List<VO> records = list.stream().skip(skip).limit(pageSize).collect(Collectors.toList());
        return page.setTotal(total).setRecords(records).setSize(pageSize).setCurrent(current);
    }

    /**
     * 基于全数据进行逻辑分页，返回
     *
     * @param list     全数据
     * @param current  当前页
     * @param pageSize 页数
     * @param <VO>     vo
     * @return {@link IPage}
     */
    default <VO> IPage<VO> bmLogicPage(List<VO> list, long current, long pageSize) {
        return logicPage(list, current, pageSize);
    }

    /**
     * 转为antdv 组件的分页参数类
     *
     * @param page        mp分页接口
     * @param moreData    附加信息
     * @param moreDataKey 附加信息key
     * @return 转为antdv 组件的分页参数类
     */
    default ResultBean<?> toAntDvPage(IPage<?> page, String moreDataKey, Object moreData) {
        if (moreDataKey == null) {
            return toAntDvPageTool(page);
        }
        return toAntDvPageTool(page).addMoreData(moreDataKey, moreData);
    }

    /**
     * 转为antdv 组件的分页参数类
     *
     * @param page x
     * @return
     */
    default ResultBean<?> toAntDvPage(IPage<?> page) {
        return toAntDvPageTool(page);
    }

    /**
     * 根据指定类型返回对应类型的mapper类（必须是extends BaseMapper<? extends BaseModel<?>>）
     *
     * @param mapper 需要强转为
     * @param <R>    泛型
     * @return 泛型
     */
    default <R extends BaseMapper<? extends BaseModel<?>>> R getBaseMapper(Class<R> mapper) {
        BaseMapper<T> baseMapper = getBaseMapper();
        R r = mapper.cast(baseMapper);
        return r;
    }

    /**
     * 抽象+断言
     *
     * @param id 实体类id
     * @return 实体类数据
     */
    default T getByIdAsset(Serializable id) {
        return getByIdAsset(id, null);
    }

    /**
     * 抽象+断言
     *
     * @param id      实体类id
     * @param message 异常信息
     * @return 实体类数据
     */
    default T getByIdAsset(Serializable id, String message) {
        T t = getById(id);
        message = StringUtils.isBlank(message) ? "根据主键id获取数据失败" : message;
        BmAssetUtils.notNull(t, message);
        return t;
    }

    /**
     * 重写（批量）删除动作（允许逻辑删除或者物理删除）+断言
     *
     * @param ids     主键id
     * @param isLogic 是否逻辑删除
     * @return 返回成功与否
     */
    default boolean removeByIdsAsset(boolean isLogic, Serializable... ids) {
        if (ids == null) {
            return false;
        }
        List<Serializable> idList = Arrays.asList(ids);
        if (isLogic) {
            idList.forEach(id -> {
                T t = getByIdAsset(id);
                t.setBmStatus(BmStatus.DELETE);
                t.updateById();
            });
            return true;
        } else {
            return removeByIds(idList);
        }
    }


    /**
     * @return 返回自带有效查询的条件（减少每次编写.eq(BaseModel::getBmStatus, BmStatus.ON)和避免没加本条件）
     */
    default LambdaQueryWrapper<T> bmLambdaQuery() {
        LambdaQueryWrapper<T> eq = Wrappers.<T>lambdaQuery().eq(T::getBmStatus, BmStatus.ON);
        return eq;
    }

    /**
     * @return 返回自带有效查询的条件（减少每次编写.eq("bm_status", BmStatus.ON.getCode())和避免没加本条件）
     */
    default QueryWrapper<T> bmQuery() {
        QueryWrapper<T> eq = Wrappers.<T>query().eq("bm_status", BmStatus.ON.getCode());
        return eq;
    }

    /**
     * @return 返回自带有效查询的条件（减少每次编写.eq(BaseModel::getBmStatus, BmStatus.ON)和避免没加本条件）
     */
    default LambdaUpdateWrapper<T> bmLambdaUpdate() {
        return Wrappers.<T>lambdaUpdate().eq(T::getBmStatus, BmStatus.ON);
    }

    /**
     * @return 返回自带有效查询的条件（减少每次编写.eq("bm_status", BmStatus.ON.getCode())和避免没加本条件）
     */
    default UpdateWrapper<T> bmUpdate() {
        return Wrappers.<T>update().eq("bm_status", BmStatus.ON.getCode());
    }
}
