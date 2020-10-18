package pri.jarod.javaweb.config;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Component;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;
import pri.jarod.javaweb.common.ResultBean;
import pri.jarod.javaweb.exception.DapException;
import pri.jarod.javaweb.exception.DapThrowException;
import pri.jarod.javaweb.exception.DescribeException;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 本类对系统异常进行aop拦截，可以自定义组装自己的异常结构
 */
@Slf4j
@Aspect
@Component
public class ControllerAOP {
    @Resource
    private BmDapConfig bmDapConfig;

    /**
     * 获取异常详细信息，知道出了什么错，错在哪个类的第几行 .
     *
     * @param ex
     * @return
     */
    private static String getThrowableDetail(Throwable ex) {
        try (StringWriter sw = new StringWriter();
             PrintWriter pw = new PrintWriter(sw, true)) {
            ex.printStackTrace(pw);
            pw.flush();
            sw.flush();
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Pointcut("execution(* pri.jarod.javaweb.controller..*.*(..))")
    public void log() {
    }

    /**
     * 源方法有问题，{@link JSON#toJSONString(java.lang.Object)} 并不是所有入参对象都是可序列化的
     * eg: ServletRequest。ServletResponse、MultipartFile
     * 进行toJSONString会报错，导致mvc传递失败
     * 修正为，若无法序列化就直接返回原值，不输出日志
     *
     * @param pjp
     * @return
     */
    @Around("log()")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result;
        Object[] args = pjp.getArgs();
        try {
            if (args == null) {
                args = new Object[]{};
            }
            Object[] arguments = Arrays.stream(args)
                    .filter(i -> !(i instanceof ServletRequest || i instanceof ServletResponse || i instanceof MultipartFile)).toArray();
            log.info("args: {}", JSON.toJSONString(arguments));
            Object proceed = pjp.proceed();
            if (proceed instanceof ResultBean<?>) {
                result = (ResultBean<?>) proceed;
            } else {
                result = proceed;
            }
            log.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        } catch (Exception e) {
            if (e instanceof DapThrowException) {
                throw e;
            } else {
                result = handlerException(pjp, e);
            }
        }
        return result;
    }

    /**
     * 封装异常信息，注意区分已知异常（自己抛出的）和未知异常
     * 2020年8月11日10:15:37 增加针对业务层抛出的异常进行组装返回
     */
    private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResultBean<?> result = ResultBean.FAIL;
        String msg = e.getMessage();
        boolean unknownException = true;
        // 已知异常
        if (e instanceof MethodArgumentNotValidException) {
            unknownException = false;
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            List<ObjectError> errors = validException.getBindingResult().getAllErrors();
            msg = errors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(";"));
        } else if (e instanceof DescribeException) {
            unknownException = false;
            msg = e.getLocalizedMessage();
        } else if (e instanceof ConstraintViolationException) {
            unknownException = false;
            ConstraintViolationException violationException = (ConstraintViolationException) e;
            msg = violationException.getLocalizedMessage();
        } else if (e instanceof DapException) {
            unknownException = false;
            DapException dapException = (DapException) e;
            msg = dapException.getMessage();
        }
        log.error(pjp.getSignature() + " error ", e);
        //未知的异常，应该格外注意，可以发送邮件通知等 发送到webhook
        result.setMsg(msg);
        result.setCode(ResultBean.FAIL_CODE);
        return result;
    }
}
