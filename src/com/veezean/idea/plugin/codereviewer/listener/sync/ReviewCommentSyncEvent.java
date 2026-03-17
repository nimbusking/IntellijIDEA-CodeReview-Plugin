package com.veezean.idea.plugin.codereviewer.listener.sync;

import com.intellij.openapi.project.Project;
import com.veezean.idea.plugin.codereviewer.model.CommitComment;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * CR数据同步事件
 * @author nimbusk
 * @since 5.0 2026-03-17
 */
public class ReviewCommentSyncEvent {

    /** 同步按钮 */
    private final JButton syncButton;

    /**
     * CR数据
     */
    private final CommitComment commitComment;
    /**
     * 当前project
     */
    private final Project project;

    /**
     * 同步结果
     */
    private final AtomicBoolean isSuccess;

    /**
     * 同步结果
     */
    private StringBuffer respMsg;

    public ReviewCommentSyncEvent(JButton syncButton, CommitComment commitComment, Project project, AtomicBoolean isSuccess, StringBuffer respMsg) {
        this.syncButton = syncButton;
        this.commitComment = commitComment;
        this.project = project;
        this.isSuccess = isSuccess;
        this.respMsg = respMsg;
    }

    public JButton getSyncButton() {
        return syncButton;
    }

    public CommitComment getCommitComment() {
        return commitComment;
    }

    public AtomicBoolean getIsSuccess() {
        return isSuccess;
    }

    public StringBuffer getRespMsg() {
        return respMsg;
    }

    public Project getProject() {
        return project;
    }
}
