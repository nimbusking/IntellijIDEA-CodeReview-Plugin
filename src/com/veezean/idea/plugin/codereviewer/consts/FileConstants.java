package com.veezean.idea.plugin.codereviewer.consts;

/**
 * 文件相关常量
 *
 * @author nimbusking
 * @since 5.0
 */
public final class FileConstants {

    private FileConstants() {}

    /**
     * 日志文件前缀
     */
    public static final String LOG_FILE_NAME_PREFIX = "CodeReviewHelperPlugin_";

    /**
     * 日志文件类型
     */
    public static final String LOG_FILE_SUFFIX = ".log";

    /**
     * 日志目录名称
     */
    public static final String LOG_HOME_NAME = ".idea_CodeReviewHelper_logs";

    /**
     * 配置文件目录
     */
    public static final String CONFIG_HOME_NAME = ".idea_CodeReviewHelper_config";

    /**
     * 配置文件名称
     */
    public static final String CONFIG_FILE_NAME = "global_config.dat";
    /**
     * 定制化本地文件名
     */
    public static final String CUSTOM_CONFIG_FILE_NAME_LOCAL = "user_custom_columns.json";

    /**
     * 定制化网络文件名
     */
    public static final String CUSTOM_CONFIG_FILE_NAME_SERVER = "server_user_custom_columns.json";

    /**
     * 系统初始化列名称
     */
    public static final String INIT_SYSTEM_COLUMNS_FILE_NAME = "SystemColumns.json";
    /**
     * CR数据目录名称
     */
    public static final String CODE_REVIEW_DATA_HOME_NAME = ".idea_CodeReviewHelper_data";

    /**
     * CR数据文件后缀
     */
    public static final String CODE_REVIEW_DATA_FILE_SUFFIX = "_comment.dat";

    /**
     * 英文语言菜单属性文件名
     */
    public static final String LANGUAGE_EN_FILE_NAME = "language_en.properties";

    /**
     * 中文语言菜单属性文件名
     */
    public static final String LANGUAGE_ZH_FILE_NAME = "language_zh.properties";
}
