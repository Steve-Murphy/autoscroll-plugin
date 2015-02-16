package com.steve.plugins.autoscroll;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.projectView.impl.ProjectViewImpl;
import com.intellij.ide.projectView.impl.ProjectViewPane;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.impl.status.StatusBarUtil;
import com.intellij.ui.AutoScrollFromSourceHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ToggleAutoScrollFromSource extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            Project project = event.getProject();
            ProjectViewImpl projectView = (ProjectViewImpl) ProjectView.getInstance(project);
            boolean toggled = !projectView.isAutoscrollFromSource(ProjectViewPane.ID);
            SetAutoScrollFromSourceHack.setAutoScrollFromSource(projectView, toggled);
            StatusBarUtil.setStatusBarInfo(project, "Autoscroll from Source is " + (toggled ? "enabled" : "disabled"));
        } catch (Exception e) {
            // ignore...
        }
    }
}
