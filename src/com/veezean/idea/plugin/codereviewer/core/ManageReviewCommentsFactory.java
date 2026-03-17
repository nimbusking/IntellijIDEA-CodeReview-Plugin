package com.veezean.idea.plugin.codereviewer.core;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.veezean.idea.plugin.codereviewer.action.ManageReviewCommentUI;
import com.veezean.idea.plugin.codereviewer.core.ProjectLevelService;
import com.veezean.idea.plugin.codereviewer.util.IconCollections;
import org.jetbrains.annotations.NotNull;

/**
 * 评审信息管理类（窗口工具类），初始化入口
 *
 * @author Veezean
 * @since 2019/9/30
 */
public class ManageReviewCommentsFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        ManageReviewCommentUI managerUI = new ManageReviewCommentUI(project);
        ProjectLevelService.getService(project).getProjectCache().setManageReviewCommentUI(managerUI);
        // 初始化入口
        managerUI.initUI();

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(managerUI.fullPanel, "", false);
        toolWindow.getContentManager().addContent(content);
        toolWindow.setIcon(IconCollections.toolWindowIcon);

    }
}
