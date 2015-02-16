package com.steve.plugins.autoscroll;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.projectView.impl.ProjectViewImpl;
import com.intellij.ide.projectView.impl.ProjectViewPane;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.impl.status.StatusBarUtil;

public class ToggleAutoScrollToSource extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            Project project = event.getProject();
            ProjectViewImpl projectView = (ProjectViewImpl) ProjectView.getInstance(project);
            boolean toggled = !projectView.isAutoscrollToSource(ProjectViewPane.ID);
            projectView.setAutoscrollToSource(toggled, ProjectViewPane.ID);
            StatusBarUtil.setStatusBarInfo(project, "Autoscroll to Source is " + (toggled ? "enabled" : "disabled"));
        } catch (Exception e) {
            // ignore...
        }
    }
}
