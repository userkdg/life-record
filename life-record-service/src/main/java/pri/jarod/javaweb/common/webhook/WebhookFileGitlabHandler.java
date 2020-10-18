package pri.jarod.javaweb.common.webhook;

import pri.jarod.javaweb.util.BmAssetUtils;
import pri.jarod.javaweb.util.JsonUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.google.common.collect.ImmutableMap;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author Jarod.Kong
 * @date 2020/10/16 10:43
 */
@Slf4j
public class WebhookFileGitlabHandler implements WebhookMsgTypeHandler {
    /**
     * {
     * "msgtype": "file",
     * "file": {
     * "media_id": "3a8asd892asd8asd"
     * }
     * }
     *
     * @param baseMap map
     * @return
     */
    @Override
    public String msgHanlder(Map<String, Object> baseMap) {
        String fileContent = Objects.toString(baseMap.get("fileContent"));
        String fileNamePrefix = Objects.toString(baseMap.get("fileNamePrefix"));
        String fileNameSuffix = Objects.toString(baseMap.get("fileNameSuffix"));
        if (fileContent != null) {
            Map<String, Object> uploadFileResp = SendFile.sendFile(fileContent, fileNamePrefix, fileNameSuffix);
            FileGitlabDto send = SendWebhook.send(uploadFileResp);
            return toJson(send);
        }
        return null;
    }

    public static final class SendWebhook {
        public static final FileGitlabDto send(Map<String, Object> weMediaData) {
            Object mediaId = weMediaData.get("media_id").toString();
            FileGitlabDto fileGitlabDto = new FileGitlabDto();
            fileGitlabDto.setMsgtype(weMediaData.get("type").toString());
            fileGitlabDto.setFile(ImmutableMap.of("media_id", mediaId.toString()));
            return fileGitlabDto;
        }
    }

    @Data
    public static final class FileGitlabDto extends WechatWebhookDto {
        private Map<String, String> file;
    }

    public static final class SendFile {

        /**
         * 上传的媒体文件限制
         * 所有文件size必须大于5个字节
         * <p>
         * 图片（image）：2MB，支持JPG,PNG格式
         * 语音（voice） ：2MB，播放长度不超过60s，仅支持AMR格式
         * 视频（video） ：10MB，支持MP4格式
         * 普通文件（file）：20MB
         * {要大于5 byte}
         * <p>
         * {
         * "errcode": 0,
         * "errmsg": "ok"，
         * "type": "file",
         * "media_id": "1G6nrLmr5EC3MMb_-zK1dDdzmd0p7cNliYu9V5w7o8K0",
         * "created_at": "1380000000"
         * }
         */
        public static Map<String, Object> sendFile(String content, String fileNamePrefix, String fileNameSuffix) {
            try {
                // 判断内容是否大于5byte
                BmAssetUtils.isFalse(content.getBytes(StandardCharsets.UTF_8).length <= 5, "文件小于5byte不可以上传");
                if (StringUtils.isBlank(fileNamePrefix)) {
                    fileNamePrefix = "dap_error_";
                }
                if (StringUtils.isBlank(fileNameSuffix)) {
                    fileNameSuffix = ".log";
                }
                Path tempFile = Files.createTempFile(fileNamePrefix, fileNameSuffix);
                Files.write(tempFile, content.getBytes(), StandardOpenOption.WRITE);
                File file = tempFile.toFile();
                HttpRequest request = HttpRequest.post(WebhookClient.WECHAR_WEBHOOK_SEND_FILE_URL);
//                File file1v  = new File("F:\\蓝月亮\\bd-data-asset-platform-service\\src\\main\\resources\\test.json");
                request.form("file", file);
                try (HttpResponse response = request.execute()) {
                    HashMap<String, Object> respData = JsonUtil.json2Map(response.body());
                    log.info("upload file, data:{}", respData);
                    return respData;
                } finally {
                    log.info("clear temp file，{}", tempFile);
                    file.deleteOnExit();
                }
            } catch (IOException e) {
                log.error("create temp file error ", e);
            }
            return Collections.emptyMap();
        }
    }
}
