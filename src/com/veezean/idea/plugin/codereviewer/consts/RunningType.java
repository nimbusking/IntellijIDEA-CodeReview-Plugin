package com.veezean.idea.plugin.codereviewer.consts;

import java.util.Arrays;

/**
 * 运行模式类型
 *
 * @author Veezean
 * @author nimbusking
 * @since 2021/6/11
 * @since 5.0 2026.02.21
 */
public enum RunningType {
    LOCAL(0, "单机版本"),
    NETWORK(1, "网络版本（私有服务器）");

    RunningType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private int value;
    private String desc;

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static RunningType getVersionType(int value) {
        return Arrays.stream(values()).filter(runningType -> runningType.getValue() == value).findFirst().orElse(LOCAL);
    }
}
