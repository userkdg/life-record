package pri.jarod.javaweb.common.auth;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.Optional;

/**
 * @author userkdg
 * @date 2020-10-17 20:04
 **/
public class UserInfoHolder implements Serializable {
    private static final ThreadLocal<UserInfo> USER_THREAD_LOCAL = ThreadLocal.withInitial(() -> null);
    private static final long serialVersionUID = 1L;

    @Data
    public static class UserInfo implements Serializable {
        private static final long serialVersionUID = 1L;
        @JSONField(name = "USER_NAME")
        private String userName;
        @JSONField(name = "ID")
        private String account;
        @JSONField(name = "MOBILE")
        private String mobile;
    }

    public static void setUserInfo(UserInfo userInfo) {
        USER_THREAD_LOCAL.set(userInfo);
    }

    public static Optional<UserInfo> getUserInfo() {
        return Optional.ofNullable(USER_THREAD_LOCAL.get());
    }

    public static UserInfo getUserInfoOrDefault(UserInfo userInfo) {
        return getUserInfo().orElse(userInfo);
    }

    public static UserInfo getUserInfoOrEmpty() {
        return getUserInfo().orElse(new UserInfo());
    }

    public static void clearUserInfo() {
        USER_THREAD_LOCAL.remove();
    }
}
