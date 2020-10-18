package pri.jarod.javaweb.excel;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Jarod.Kong
 * @date 2020/8/26 10:25
 */
public abstract class BaseCacheVerifyHandler {
    /**
     * 行号工具
     */
    protected final AtomicInteger sheetRowIndex = new AtomicInteger(1);

    /**
     * @return 获取处理excel过程中的缓存
     */
    public abstract Map<String, Object> getSheetCacheMap();
}
