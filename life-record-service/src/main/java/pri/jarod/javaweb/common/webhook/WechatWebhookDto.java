package pri.jarod.javaweb.common.webhook;

/**
 * @author Jarod Kong
 * @date 2020-09-19 14:29
 **/
public abstract class WechatWebhookDto {
    public static final String markdownMsgType = "markdown";

    public static final String newsMsgType = "news";

    protected String msgtype;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}
