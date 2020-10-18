package pri.jarod.javaweb.common;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 抽象基础的增删该查逻辑
 *
 * @author Jarod.Kong
 * @date 2020/8/14 15:21
 */
@Slf4j
public abstract class BaseCrudController<E extends BaseModel<E>> implements IController<E>{
    @ApiResponses({@ApiResponse(code = 200, message="ok", response = ResultBean.class)})
    @PostMapping({""})
    @ApiOperation("新增数据")
    public ResultBean<?> add(@RequestBody @Valid E e) {
        getService().save(e);
        return ResultBean.ok(e);
    }

    @ApiResponses({@ApiResponse(code = 200, message="ok", response = ResultBean.class)})
    @DeleteMapping({"/{id}"})
    @ApiOperation("删除数据")
    public ResultBean<?> delete(@PathVariable String id) {
        getService().removeById(id);
        return ResultBean.ok();
    }

    @ApiResponses({@ApiResponse(code = 200, message="ok", response = ResultBean.class)})
    @PutMapping({"/{id}"})
    @ApiOperation("修改数据")
    public ResultBean<?> edit(@RequestBody @Valid E e, @PathVariable String id) {
        UpdateWrapper<E> upwrapper = new UpdateWrapper<E>(e).eq("id", id);
        getService().update(upwrapper);
        return ResultBean.ok(e);
    }

    @ApiResponses({@ApiResponse(code = 200, message="ok", response = ResultBean.class)})
    @DeleteMapping({"/{id}"})
    @ApiOperation("新增数据")
    public ResultBean<?> get(@PathVariable String id) {
        return ResultBean.ok(getService().getById(id));
    }


}
