package pri.jarod.javaweb.constant;

import pri.jarod.javaweb.config.CacheConfig;

/**
 * @author Jarod.Kong
 * @date 2020/8/24 10:25
 */
public final class DapConstant {

    /**
     * {@link CacheConfig#dapKeyGenerator()}
     */
    public static final String CACHE_KEY_GENERATOR = "dapKeyGenerator";
    public static final String WEB_HTTP_TOKEN_NAME = "dap-token";

    /**
     * 中文检查报告 标准检查报告
     * 表&字段结构完整性检查
     */
    public static final String AUDIT_SYSTEM_REPORT_CHINESE = "AUDIT_SYSTEM_REPORT_CHINESE";
    public static final String AUDIT_SYSTEM_REPORT_STANDARD = "AUDIT_SYSTEM_REPORT_STANDARD";
    public static final String AUDIT_SYSTEM_REPORT_FIELD_CHINESE = "AUDIT_SYSTEM_REPORT_FIELD_CHINESE";
    public static final String AUDIT_SYSTEM_REPORT_FIELD_STANDARD = "AUDIT_SYSTEM_REPORT_FIELD_STANDARD";
    public static final String AUDIT_SYSTEM_REPORT_MAIN = "AUDIT_SYSTEM_REPORT_MAIN";

    /**
     * 目前用来维护每次会议变更的内容，方便在其他代码块中通过{@link}或{@see}等进行引入，从而跟进最新变化
     */
    public static class VersionMark {
        /**
         * 审批流程中的撤销功能
         */
        public static final String STANDARD_MODIFY_REVERT =
                "v1:审批流程中的撤销功能：针对待审批状态流程进行撤销，调整状态为草稿" +
                        "v1.1.0 2020年9月9日09:53:35后，审批流程中的撤销功能：只针对草稿或不通过状态的流程状态数据进行删除";
        /**
         * 标准数据导入功能调整
         */
        private static final String STANDARD_EXCEL_IMPORT =
                "@since 2020年9月10日17:10:16 在线会议确定，导入excel数据标准中导入的数据状态从之前的【待审批】调整为【草稿】";
    }

    /**
     * 数据标准的常量
     */
    public static class DapStandard {
        /**
         * 缓存名 对应{@link /ehcache.xml} 的name
         */
        public static final String CACHE_NAME = "dap-standard-cache";

        public static final String STANDARD_IMPORT_FILE_SHEET1_NAME = "基础数据标准列表";
        public static final String STANDARD_IMPORT_FILE_SHEET2_NAME = "基础代码标准列表";
        public static final String STANDARD_IMPORT_FILE_SHEET3_NAME = "基础代码值列表";
        public static final String STANDARD_IMPORT_FILE_SHEET4_NAME = "校验规则";
        public static final String SYSTEM_TOPIC1_NAME = "topic1";
        public static final String SYSTEM_TOPIC2_NAME = "topic2";
        public static final String SYSTEM_CATEGORY1_NAME = "category1";
        public static final String SYSTEM_CATEGORY2_NAME = "category2";
        public static final String SYSTEM_CATEGORY3_NAME = "category3";
        private static final String DETAIL = "明细";
        public static final String STANDARD_IMPORT_FILE_SHEET1_NAME_DETAIL = "基础数据标准列表" + DETAIL;
        public static final String STANDARD_IMPORT_FILE_SHEET2_NAME_DETAIL = "基础代码标准列表" + DETAIL;
        public static final String STANDARD_IMPORT_FILE_SHEET3_NAME_DETAIL = "基础代码值列表" + DETAIL;

        public enum StandardType {
            BASIC(0),
            INDEX(1);
            private final int code;

            StandardType(int code) {
                this.code = code;
            }

            public int getCode() {
                return code;
            }
        }
    }

    /**
     * 数据模型中的常量
     */
    public static class DapModel {
        public static final String CACHE_NAME = "dap-model-cache";

        /**
         * 限定词类
         */
        public enum NameRecommendFieldType {
            限定词(1),
            类别词(3),
            主题词(2),
            其他词(-1);
            private final int type;

            NameRecommendFieldType(int type) {
                this.type = type;
            }

            public int getType() {
                return type;
            }
        }
    }
}
