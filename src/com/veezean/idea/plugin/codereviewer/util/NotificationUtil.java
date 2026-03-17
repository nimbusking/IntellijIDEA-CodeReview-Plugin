package com.veezean.idea.plugin.codereviewer.util;

import com.intellij.notification.*;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.veezean.idea.plugin.codereviewer.core.ProjectLevelService;

import java.text.MessageFormat;

/**
 * IDE级别事件通知工具类
 * @author nimbusking
 * @since 5.0
 */
public class NotificationUtil {

    private NotificationUtil() {}


    /**
     * 发送成功/失败操作结果通知
     *
     * @param project 当前项目
     * @param actionKey 操作名称的 key，如 "action.import"
     * @param success 是否成功
     */
    public static void notificationResult(Project project, String actionKey, boolean success, String extraMsg) {
        String actionName = getMessage(actionKey); // 获取本地化操作名
        String result = getMessage(success ? "RESULT_SUCCESS" : "RESULT_FAILURE");
        if (!success) {
            result = result + System.lineSeparator() + extraMsg;
        }
        String template = getMessage("NOTIFICATION_TEMPLATE");

        String content = MessageFormat.format(template, actionName, result);
        NotificationGroup notificationGroup = ProjectLevelService.getService(project).getNotificationGroup();
        Notification notification = notificationGroup.createNotification(content, success ? NotificationType.INFORMATION : NotificationType.ERROR);
        Notifications.Bus.notify(notification, project);
    }

    private static String getMessage(String key) {
        return LanguageUtil.getString(key);
    }

    /**
     * Warning级别提醒
     * @param notificationGroup notificationGroup
     * @param msg 提醒消息
     */
    public static void notifyWarning(NotificationGroup notificationGroup, String msg) {
        Notification notification = notificationGroup.createNotification(msg, MessageType.WARNING);
        Notifications.Bus.notify(notification);
    }

}
