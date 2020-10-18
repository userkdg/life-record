package pri.jarod.javaweb.service;

import pri.jarod.javaweb.common.auth.UserInfoHolder;

public interface UserService {
    String isLoginUser(String token);

    UserInfoHolder.UserInfo getUserInfoByUserId(String userAccount);
}
