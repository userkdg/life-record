package pri.jarod.javaweb.common.webhook;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jarod Kong
 * @date 2020-09-19 14:11
 **/
public class WebhookNewsGitlabHandler implements WebhookMsgTypeHandler {
    @Override
    public String msgHanlder(Map<String, Object> map) {
        Object userName = map.get("user_name");
        Object userEmail = map.get("user_email");
        Object userAvatar = map.get("user_avatar");
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<Map<String, Object>> commits = (List) map.get("commits");
        Map<String, Object> commit = commits.get(0);
        String url = Objects.toString(commit.get("url"));
        String message = Objects.toString(commit.get("message"));
        String appName = "数据资产项目";
        WechatNewsWebhookDto reqBody = new WechatNewsWebhookDto(
                new WechatNewsWebhookDto.News(
                        Collections.singletonList(new WechatNewsWebhookDto.News.Article(
                                String.format("关于%s构建", appName),
                                String.format("%s最新push信息：%s", userName, message),
                                Objects.toString(url),
                                Objects.toString(userAvatar)
                        )))
        );
        return toJson(reqBody);
    }
}
