//package pri.jarod.javaweb.feign;
//
//import pri.jarod.javaweb.common.auth.FeignApiRespBean;
//import com.alibaba.fastjson.JSONObject;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import pri.jarod.javaweb.common.auth.UserInfoHolder;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//
//@FeignClient(value = "bd-service-user")
//@RequestMapping("/user")
//public interface UserService {
//
//    /**
//     * 判断是否登陆,是则返回用户账号
//     *
//     * @param token
//     * @return
//     */
//    @RequestMapping("/isLoginUser")
//    public String isLoginUser(@RequestParam("token") String token);
//
//    /**
//     * 判断是否登陆SFA,是则返回用户账号
//     *
//     * @param token
//     * @return
//     */
//    @RequestMapping("/isLoginUserSFA")
//    public String isLoginUserSFA(@RequestParam("token") String token);
//
//    /**
//     * 通过天使获取帐号
//     *
//     * @param token
//     * @return
//     */
//    @RequestMapping("/getAccountByAngel")
//    public String getAccountByAngel(@RequestParam("token") String token);
//
//    /**
//     * 通过SFA获取帐号
//     *
//     * @param token
//     * @return
//     */
//    @RequestMapping("/getAccountBySFA")
//    public String getAccountBySFA(@RequestParam("token") String token);
//
//    /**
//     * 通过userId获得用户信息
//     *
//     * @param userId
//     * @return
//     */
//    @RequestMapping("/getUserInfoById")
//    public List<Map<String, Object>> getUserInfoById(@RequestParam("userId") String userId);
//
//    /**
//     * @param userId
//     * @return
//     */
//    default UserInfoHolder.UserInfo getUserInfoByUserId(String userId) {
//        List<Map<String, Object>> userInfoById = getUserInfoById(userId);
//        if (userInfoById != null) {
//            Map<String, Object> map = userInfoById.stream().findFirst().orElse(Collections.emptyMap());
//            UserInfoHolder.UserInfo userInfo = new UserInfoHolder.UserInfo();
//            userInfo.setAccount(map.getOrDefault("ID", "").toString());
//            userInfo.setMobile(map.getOrDefault("MOBILE", "").toString());
//            userInfo.setUserName(map.getOrDefault("USER_NAME", "").toString());
//            return userInfo;
//        }
//        return null;
//    }
//
//    /**
//     * 根据userId获取token
//     *
//     * @param userId
//     * @return
//     */
//    @RequestMapping("/getToken")
//    public String getToken(@RequestParam("userId") String userId);
//
//    /**
//     * 从天使中登陆
//     *
//     * @param username 用户名
//     * @param password 加密后密码
//     * @return
//     */
//    @RequestMapping("/loginAngel")
//    public String loginAngel(@RequestParam("username") String username, @RequestParam("password") String password);
//
//    /**
//     * 从DOMP中登陆后，通过UserService把token和username放入redis
//     *
//     * @param username 用户名
//     * @param token    用户标识token
//     * @return
//     */
//    @RequestMapping("/loginDOMP")
//    public String loginDOMP(@RequestParam("username") String username, @RequestParam("token") String token);
//
//    /**
//     * 通过token检查用户是否为BMBI管理员，是管理员则返回管理员ID
//     *
//     * @param token 用户标识token
//     * @return
//     */
//    @RequestMapping("/isAdminBMBI")
//    public String isAdminBMBI(@RequestParam("token") String token);
//
//
//    /**
//     * 用户登陆
//     *
//     * @param userName 用户名
//     * @param passWord 密码
//     * @throws Exception 注意：页面传过来的密码为AES加密后的密码，发送给天使的密码为DES加密后的密码，
//     *                   保存在domp数据库中的的密码为md5加密后的密码
//     *                   domp默认密码为tR4362do
//     */
//    @RequestMapping(value = "/login")
//    public String login(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord);
//
//    default FeignApiRespBean<Map<String, Object>> bmLogin(String user, String pwd) {
//        String respData = login(user, pwd);
//        FeignApiRespBean feignApiRespBean = JSONObject.parseObject(respData, FeignApiRespBean.class);
//        return feignApiRespBean;
//    }
//
//    /**
//     * 用户加密登陆
//     *
//     * @param userName 用户名
//     * @param passWord 密码（经过加密的密码）
//     */
//    @RequestMapping(value = "/encryptLogin")
//    public String encryptLogin(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord);
//}
