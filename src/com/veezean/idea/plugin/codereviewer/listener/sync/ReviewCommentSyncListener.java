package com.veezean.idea.plugin.codereviewer.listener.sync;

import com.alibaba.fastjson.TypeReference;
import com.google.common.eventbus.Subscribe;
import com.intellij.openapi.project.Project;
import com.veezean.idea.plugin.codereviewer.common.NetworkOperationHelper;
import com.veezean.idea.plugin.codereviewer.consts.Constants;
import com.veezean.idea.plugin.codereviewer.model.CommitComment;
import com.veezean.idea.plugin.codereviewer.model.CommitResult;
import com.veezean.idea.plugin.codereviewer.model.Response;
import com.veezean.idea.plugin.codereviewer.model.ReviewComment;
import com.veezean.idea.plugin.codereviewer.core.ProjectLevelService;
import com.veezean.idea.plugin.codereviewer.util.CommonUtil;
import com.veezean.idea.plugin.codereviewer.util.Logger;

import javax.swing.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

/**
 * CR数据同步事件监听类
 * @author nimbusk
 * @since 5.0 2026-03-17
 */
public class ReviewCommentSyncListener {

    @Subscribe
    public void sync(ReviewCommentSyncEvent syncEvent) {
        Logger.info("接受远程提交事件, 开始提交...");
        JButton commitToServerButton = syncEvent.getSyncButton();
        CommitComment commitComment = syncEvent.getCommitComment();
        AtomicBoolean isSuccess = syncEvent.getIsSuccess();
        StringBuffer errInfo = syncEvent.getRespMsg();
        Project project = syncEvent.getProject();
        try {
            if (commitToServerButton != null) {
                commitToServerButton.setEnabled(false);
            }
            NetworkOperationHelper.doPost("client/comment/commitComments",
                    commitComment,
                    new TypeReference<Response<CommitResult>>() {
                    },
                    respBody -> {
                        CommitResult commitResult = respBody.getData();
                        if (!commitResult.isSuccess()) {
                            errInfo.append(commitResult.getErrDesc())
                                    .append(System.lineSeparator());
                            if (commitResult.getFailedIds() != null) {
                                errInfo.append(
                                        commitResult.getFailedIds().stream().collect(Collectors.joining(",",
                                                "[", "]"))
                                );
                            }
                            isSuccess.set(false);
                        }

                        List<ReviewComment> cachedComments =
                                ProjectLevelService.getService(project)
                                        .getProjectCache()
                                        .getCachedComments();

                        // 更新提交完成的状态标识
                        cachedComments.stream()
                                .filter(reviewComment -> commitResult.getFailedIds() != null
                                        && !commitResult.getFailedIds().contains(reviewComment.getId()))
                                .forEach(reviewComment -> {
                                    // 提交成功的记录，更新状态为已提交
                                    reviewComment.setCommitFlag(Constants.NOT_CHANGED);
                                });

                        Map<String, Long> versionMap = commitResult.getVersionMap();
                        if (versionMap != null) {
                            cachedComments.forEach(reviewComment -> {
                                Long version = versionMap.get(reviewComment.getId());
                                if (version != null) {
                                    reviewComment.setDataVersion(version);
                                }
                            });
                        }

                        // 写入本地，并刷新表格显示
                        ProjectLevelService.getService(project).getProjectCache()
                                .importComments(cachedComments);
                        CommonUtil.reloadCommentListShow(project);

                    }
            );
        } catch (Exception ex) {
            Logger.error("上传评审数据失败", ex);
            isSuccess.set(false);
            errInfo.append(System.lineSeparator()).append(ex.getMessage());
        } finally {
            if (commitToServerButton != null) {
                commitToServerButton.setEnabled(true);
            }
        }
        Logger.info("接受远程提交事件, 结束提交...");
    }

}
