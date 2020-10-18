package pri.jarod.javaweb.common.auth;

import java.io.Serializable;

import static org.apache.commons.lang.builder.ToStringBuilder.reflectionToString;
import static org.apache.commons.lang.builder.ToStringStyle.MULTI_LINE_STYLE;

/**
 * @author Jarod.Kong
 * @date 2020/10/16 15:50
 */
public class FeignApiRespBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 响应代码
     */
    private String respCode;

    /**
     * 响应内容
     */
    private T respData;

    /**
     * 响应描述
     */
    private String respDesc;

    public T getRespData() {
        return (T) respData;
    }

    public String getRespCode() {
        return respCode;
    }

    public String getRespDesc() {
        return respDesc;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public void setRespData(T respData) {
        this.respData = respData;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    @Override
    public String toString() {
        return reflectionToString(this, MULTI_LINE_STYLE);
    }
}
