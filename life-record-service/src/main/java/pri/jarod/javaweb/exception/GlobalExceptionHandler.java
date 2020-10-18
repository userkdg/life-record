package pri.jarod.javaweb.exception;

import pri.jarod.javaweb.common.ResultBean;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pri.jarod.javaweb.config.ControllerAOP;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 本类针对前端请求后端过程中，进行数据校验，若不合法则捕获进行异常处理，然后返回给前端
 * 而{@link ControllerAOP} 是对系统业务逻辑的异常进行处理，然后返回给前端
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = DapException.class)
    public ResultBean<?> method(HttpServletRequest req, DapException ex) {
        ResultBean<?> result = ResultBean.FAIL;
        String errorMsg = ex.getMessage();
        log.error("---DapException Handler--- ERROR: {}", errorMsg);
        result.setMsg(errorMsg);
        result.setCode(ex.getCode());
        return result;
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultBean<?> methodArgumentNotValid(HttpServletRequest req, MethodArgumentNotValidException ex) {
        ResultBean<?> result = ResultBean.FAIL;
        List<ObjectError> errors = ex.getBindingResult().getAllErrors();
        String errorMsg = errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).distinct().collect(Collectors.joining(";"));
        log.error("---MethodArgumentNotValidException Handler--- ERROR: {}", errorMsg);
        result.setMsg(errorMsg);
        return result;
    }
}
