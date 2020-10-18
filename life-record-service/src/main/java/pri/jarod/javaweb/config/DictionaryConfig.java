package pri.jarod.javaweb.config;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

/**
 * @author Jarod.Kong
 * @date 2020/9/11 8:38
 */
@Slf4j
public enum DictionaryConfig {
    INSTANCE;

    /**
     * 缓存大小初始化为1000000
     */
    public static final int CACHE_MAP_SIE = 1000000;

    /**
     * late init
     */
    private Cache<String, Double> tfCache;

    private DictionaryConfig() {
    }

//    public static void main(String[] args) {
//        Cache<String, Double> tfCache = DictionaryConfig.INSTANCE.getTfCache();
//        ConcurrentMap<String, Double> stringDoubleConcurrentMap = DictionaryConfig.INSTANCE.getTf_IDFMap();
//        System.out.println();
//        //获取inu模板文件
//        Resource resource = new ClassPathResource("dictionary" + File.separator + "custom");
//            File dicFile = Files.createTempFile("dictionary_", ".txt").toFile();
//                Files.copy(resource.getInputStream(), dicFile.toPath());
//    }

    /**
     * 文件数据每行转为Map
     *
     * @param file 文件或者目录（遍历目录下）
     * @return 文件数据每行转为Map
     * @throws IOException
     */
    private static Map<String, Double> asMapByFile(File file) throws IOException {
        if (file == null || !file.exists()) {
            return Collections.emptyMap();
        }
        if (file.isFile()) {
            return readFileToMap(file.toPath());
        }
        return Files.walk(file.toPath(), FileVisitOption.values())
                .filter(p -> p.toString().endsWith(".txt"))
                .flatMap(p -> readFileToMap(p).entrySet().stream())
                .collect(Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b));
    }

    /**
     * 根据Path 生成map
     *
     * @param p 根据Path
     * @return 生成map
     */
    private static Map<String, Double> readFileToMap(Path p) {
        Map<String, Double> tfMap = Maps.newHashMap();
        try {
            List<String> list = Files.readAllLines(p);
            list.forEach(s -> {
                String[] cols = s.split(" ", -1);
                if (cols.length <= 1) {
                    cols = s.split("\t", -1);
                }
                // a
                // 丁·穆罕默德·莫巴里兹·拉希迪 nrf 1
                // 丁一宇 nr 15
                if (cols.length <= 2) {
                    tfMap.put(cols[0], 1D);
                } else {
                    Double tfValue = 1D;
                    try {
                        tfValue = Double.valueOf(cols[2]);
                    } catch (NumberFormatException e) {
                        log.error("字符串转double异常", e);
                    }
                    tfMap.put(cols[0], tfValue);
                }
            });
        } catch (IOException e) {
            log.error("读取文件异常", e);
        }
        return tfMap;
    }

    /**
     * 生成全局Cache缓存
     *
     * @return tf-idf
     */
    public Cache<String, Double> getTfCache() {
        if (tfCache == null) {
            tfCache = CacheBuilder.<String, Double>newBuilder().initialCapacity(CACHE_MAP_SIE).build();
            Map<String, Double> tfData = loadTfDataFromResources();
            tfCache.putAll(tfData);
            log.info("首次初始化TF-IDF字典数据，条数：{}", tfData.size());
        }
        return tfCache;
    }

    /**
     * @return 返回tf缓存数据
     */
    public Map<String, Double> getTf_IDFMap() {
        if (tfCache == null) {
            getTfCache();
        }
        ConcurrentMap<String, Double> tfIdfMap = tfCache.asMap();
        return tfIdfMap;
    }

    /**
     * 初始化字典数据
     *
     * @return
     */
    private Map<String, Double> loadTfDataFromResources() {
        try {
            File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "dictionary" + File.separator + "custom");
            File myDicFile = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "dictionary" + File.separator + "mydic");
            Map<String, Double> asMap = asMapByFile(file);
            // 自定义字典的词 覆盖字典数据
            Map<String, Double> myDicMap = asMapByFile(myDicFile);
            asMap.putAll(myDicMap);
            return asMap;
        } catch (IOException e) {
            log.error("读取文件异常", e);
        }
        return Collections.emptyMap();
    }
}
