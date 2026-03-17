package com.veezean.idea.plugin.codereviewer.consts;

import com.intellij.openapi.util.Key;

import java.awt.*;

/**
 * 常量类
 *
 * @author Veezean
 * @since 2021/4/26
 */
public class Constants {

    public static final int ADD_COMMENT = 0;
    public static final int DETAIL_COMMENT = 1;
    public static final String UNCONFIRMED = "unconfirmed";

    public static final String CODE_REVIEW_HELPER_MARKER = "CODE_REVIEW_HELPER_MARKER";
    public static final String CODE_REVIEW_HELPER_MARKER_UNCONFIRMED = "CODE_REVIEW_HELPER_MARKER_UNCONFIRMED";

    public static final Key<Object> HIGHTLIGHT_MARKER = Key.create(CODE_REVIEW_HELPER_MARKER);

    public static final String BASE_PACKAGE = "com.veezean";

    /**
     * 有改动且未提交
     */
    public static final int UNCOMMITED = 1;
    /**
     * 本地无改动
     */
    public static final int NOT_CHANGED = 0;

    /**
     * 单元格前景色-未确认
     */
    public static Color COLOR_UNCONFIRMED = new Color(196, 135, 122 );

    /**
     * 单元格前景色-已修改
     */
    public static Color COLOR_EDITED = new Color(33, 198, 70 );
}
