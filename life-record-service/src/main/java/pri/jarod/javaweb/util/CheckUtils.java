package pri.jarod.javaweb.util;

import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CheckUtils {

    /** 全中文判断 */
    private static final String  CONTENT_CHINESE_REGEX   = "^[\u4e00-\u9fa5]+$";
    private static final Pattern CONTENT_CHINESE_PATTERN = Pattern.compile(CONTENT_CHINESE_REGEX);


    /**
     * 判断所有的字符是否都是中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        Matcher matcher = CONTENT_CHINESE_PATTERN.matcher(str.trim());
        return matcher.matches();
    }

    /**
     * 判断集合中是否有重复的数据
     * @param list
     * @return
     */
    public static List<String> getDuplicateElements(List<String> list){
        return list.stream()
                .collect(Collectors.toMap(e -> e, e -> 1, (a, b) -> a + b))
                .entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(entry -> entry.getKey())
                .collect(Collectors.toList());
    }
}
