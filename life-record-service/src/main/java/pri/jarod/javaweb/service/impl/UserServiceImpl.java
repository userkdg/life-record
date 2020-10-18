package pri.jarod.javaweb.service.impl;

import org.springframework.stereotype.Service;
import pri.jarod.javaweb.common.auth.UserInfoHolder;
import pri.jarod.javaweb.service.UserService;

/**
 * @author userkdg
 * @date 2020-10-18 14:19
 **/
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String isLoginUser(String token) {
        return null;
    }

    @Override
    public UserInfoHolder.UserInfo getUserInfoByUserId(String userAccount) {
        return null;
    }
}
