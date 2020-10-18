package pri.jarod.javaweb.common;

import pri.jarod.javaweb.common.auth.FeignApiRespBean;
import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * common return
 *
 * @param <T>
 * @author xuxueli 2015-12-4 16:32:31
 * @author Jarod Kong Deprecated new 构造器，转为静态获取，增加分页，增加addMoreData,增加泛型
 * @author Jarod Kong 增加cn.com.bluemoon.dap.common.ResultBean#of(cn.com.bluemoon.dap.common.auth.FeignApiRespBean)
 * 用于feign调用返回的数据，转为ResultBean
 */
public class ResultBean<T> implements Serializable {
    public static final long serialVersionUID = 42L;
    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;
    /**
     * 无权限 & 拒绝访问
     */
    public static final ResultBean<String> UNAUTHORIZED = new ResultBean<>(HttpStatus.UNAUTHORIZED.value(), "无权限访问");
    public static final ResultBean<String> FORBIDDEN = new ResultBean<>(HttpStatus.FORBIDDEN.value(), "认证无效，禁止访问");

    public static final ResultBean<String> SUCCESS = new ResultBean<String>(null);
    public static final ResultBean<String> FAIL = new ResultBean<String>(FAIL_CODE, "fail");

    private int code;
    private String msg = "success";
    private T content;
    private boolean isPageContent;
    /**
     * 增加附加信息属性
     * late init
     */
    private Map<String, Object> moreData;

    private ResultBean(int code, String msg, T content) {
        this.content = content;
        this.code = code;
        this.msg = msg;
    }

    private ResultBean(int code, String msg, T content, boolean isPageContent) {
        this.content = content;
        this.code = code;
        this.msg = msg;
        this.isPageContent = isPageContent;
    }

    @Deprecated
    public ResultBean() {
    }

    private ResultBean(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResultBean(T content) {
        this.code = SUCCESS_CODE;
        this.content = content;
    }

    private ResultBean(T content, boolean isPageContent) {
        this.code = SUCCESS_CODE;
        this.content = content;
        this.isPageContent = isPageContent;
    }

    public static <T> ResultBean<T> error(String msg) {
        return new ResultBean<>(FAIL_CODE, msg);
    }

    public static <T> ResultBean<T> data(int code, T data) {
        return new ResultBean<>(code, null, data);
    }

    public static <T> ResultBean<T> ok(T data) {
        return new ResultBean<>(data);
    }

    public static ResultBean<String> ok() {
        return SUCCESS;
    }

    public static <T> ResultBean<T> page(T data) {
        return new ResultBean<>(data, true);
    }

    /**
     * feign返回转未this
     *
     * @param feignApiRespBean
     * @param <T>
     * @return
     */
    public static <T> ResultBean<T> of(FeignApiRespBean<T> feignApiRespBean) {
        int code = -1;
        if (NumberUtil.isNumber(feignApiRespBean.getRespCode())) {
            code = NumberUtil.parseInt(feignApiRespBean.getRespCode());
        }
        // feign的100对应200正常
        if (code == 100) {
            code = 200;
        }
        return new ResultBean<>(code, feignApiRespBean.getRespDesc(), feignApiRespBean.getRespData());
    }

    public int getCode() {
        return code;
    }

    public ResultBean<T> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResultBean<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getContent() {
        return content;
    }

    public ResultBean<T> setContent(T content) {
        this.content = content;
        return this;
    }

    public boolean isPageContent() {
        return isPageContent;
    }

    public void setPageContent(boolean pageContent) {
        isPageContent = pageContent;
    }

    @Override
    public String toString() {
        return "ResultBean [code=" + code + ", msg=" + msg + ", content=" + content + "]";
    }

    public ResultBean<?> toAntDvPage() {
        if (this.isPageContent && this.content instanceof IPage) {
            AntDvPage<T> dvPage = new AntDvPage<>((IPage<T>) this.content);
            return new ResultBean<>(this.code, this.msg, dvPage, this.isPageContent);
        }
        return new ResultBean<>(this.code, this.msg, this.content, this.isPageContent);
    }

    /**
     * 针对分页数据为空 导致前端获取基本属性失败的问题
     * 比如{@link AntDvPage#getTotal()} 等
     * 非分页对象，返回原值
     *
     * @return
     */
    public ResultBean<?> orElseEmptyPage() {
        if (this.isPageContent && this.content == null) {
            return new ResultBean<>(this.code, this.msg, AntDvPage.empty(), this.isPageContent);
        }
        return new ResultBean<>(this.code, this.msg, this.content, false);
    }

    /**
     * 增加附加信息属性
     *
     * @param key   属性
     * @param value 属性值
     * @return 当前对象
     */
    public ResultBean<T> addMoreData(String key, Object value) {
        if (this.moreData == null) {
            this.moreData = new LinkedHashMap<>();
        }
        this.moreData.put(key, value);
        return this;
    }

    public Map<String, Object> getMoreData() {
        return moreData;
    }
}
