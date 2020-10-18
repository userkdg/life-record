package pri.jarod.javaweb.config;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.NumberUtil;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

/**
 * standard-allow-suffix:
 * - .xls
 * - .xlxs
 * save: true
 * dir-date-format: yyyy/MM/dd
 * save-base-path: /dap-standard-upload-file/store/${dir-date-format}
 * #  -1 永久   s 秒 min 分 h 时 d 天 m 月 y 年
 * ttl: 7d
 *
 * @author Jarod.Kong
 * @date 2020/8/25 9:32
 */
@Configuration
@ConfigurationProperties(prefix = "bm.dap", ignoreUnknownFields = true)
@Data
@Slf4j
public class BmDapConfig {

    private String appName;

    @NestedConfigurationProperty
    private MyException exception = new MyException();

    private List<String> constantMap;

    /**
     * 嵌套属性
     */
    @NestedConfigurationProperty
    private FileUploadProperties file = new FileUploadProperties();

    /**
     * 别名
     *
     * @return
     */
    public FileUploadProperties getFileUploadProperties(){
        return getFile();
    }

    @Data
    public static class MyException{
        private Boolean sendWebhook;
        private List<String> sendMails;
    }

    @Data
    public static class FileUploadProperties{
        /**
         * 允许的文件后缀
         */
        private List<String> standardAllowSuffix;
        /**
         * 是否保存上传文件
         */
        private boolean save;
        private String dirDateFormat;
        private String saveBasePath;
        private String ttl;
        /**
         * ttl-cron-timer
         */
        private String ttlCronTimer;
        /**
         * 临时目录
         */
        private String saveFileTempDir;
    }

    /**
     * 调度线程进行清理过期文件
     * 1.每天目录下没文件，跳出
     * 2.有文件则检查lastModifyTime 大于ttl则清理，否则保留
     */
    public BmDapConfig() {
        // 启动检查一次，后续调度检查
//        scheduleFileTTL();
    }

    /**
     * 获取配置对应的文件路径
     *
     * @param filename
     * @return Optional.empty() 为不保存文件
     */
    public Optional<String> standardStorePathOpt(String filename) {
        FileUploadProperties props = getFile();
        if (props.isSave()) {
            String lastDir = "";
            if (StringUtils.isNotBlank(props.getDirDateFormat())) {
                lastDir = LocalDate.now().format(DateTimeFormatter.ofPattern(props.getDirDateFormat()));
            }
            String basePath = props.getSaveBasePath() + "/" + lastDir;
            if (StringUtils.isBlank(filename)) {
                filename = UUID.fastUUID().toString();
            }
            return Optional.of(basePath + "/" + filename);
        }
        log.warn("当前配置不对标准导入文件进行存储");
        return Optional.empty();
    }

    /**
     * 调度触发系统保存文件，进行【开始检查上传文件路径下的文件有效期】
     */
    @SneakyThrows
//    @Scheduled(cron = "${bm.dap.file.ttl-cron-timer}")
    public void scheduleFileTTL() {
        FileUploadProperties props = getFile();
        if (props.isSave()) {
            // 启动调度检查上传的历史文件保存周期
            log.info("开始检查上传文件路径下的文件有效期");
            String saveBasePath = props.getSaveBasePath();
            Path basePath = Paths.get(saveBasePath);
            if (Files.notExists(basePath)) return;
            LocalDateTime now = LocalDateTime.now();
            // 转second
            String ttl = props.getTtl();
            long ttlSecond = ttlSecond(ttl);
            LocalDateTime preDateTime = now.minusSeconds(ttlSecond);
            Files.walk(basePath, FileVisitOption.values())
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .filter(file -> preDateTime.isAfter(new Timestamp(file.lastModified()).toLocalDateTime()))
                    .forEach(file -> {
                        try {
                            Files.deleteIfExists(file.toPath());
                            log.info("清理过期文件：{}", file.toPath());
                        } catch (IOException e) {
                            log.info("清理过期文件：{}，失败：{}", file.toPath(), e);
                        }
                    });
        }
    }

    /**
     * 转为统一秒long
     * 0 调度触发即可删除  -1 永久   s 秒 min 分 h 时 d 天 m 月 y 年
     *
     * ttl: 7d
     *
     * @param ttl
     * @return
     */
    private static long ttlSecond(String ttl) {
        if (ttl == null) {
            return 0L;
        }
        // -1 永久   s 秒 min 分 h 时 d 天 m 月 y 年
        ttl = ttl.trim().toLowerCase();
        if ("-1".equals(ttl)) {
            return -1L;
        } else if (ttl.endsWith("s") || NumberUtil.isNumber(ttl)) {
            return Long.parseLong(ttl.replace("s", ""));
        } else if (ttl.endsWith("min")) {
            return Long.parseLong(ttl.replace("min", "")) * 60;
        } else if (ttl.endsWith("h")) {
            return Long.parseLong(ttl.replace("h", "")) * 60 * 60;
        } else if (ttl.endsWith("d")) {
            return Long.parseLong(ttl.replace("d", "")) * 60 * 60 * 24;
        } else if (ttl.endsWith("m")) {
            return Long.parseLong(ttl.replace("m", "")) * 60 * 60 * 24 * 30;
        } else if (ttl.endsWith("y")) {
            return Long.parseLong(ttl.replace("y", "")) * 60 * 60 * 24 * 30 * 365;
        } else {
            return 0;
        }
    }

}
