package com.steve.plugins.autoscroll;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.projectView.impl.ProjectViewImpl;
import com.intellij.ide.projectView.impl.ProjectViewPane;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.wm.impl.status.StatusBarUtil;

public class SaveAutoScrollSettings extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        try {
            Project project = event.getProject();
            ProjectViewImpl projectView = (ProjectViewImpl) ProjectView.getInstance(project);
            boolean to = projectView.isAutoscrollToSource(ProjectViewPane.ID);
            boolean from = projectView.isAutoscrollFromSource(ProjectViewPane.ID);
            AutoScrollComponent component = ApplicationManager.getApplication().getComponent(AutoScrollComponent.class);
            component.setState(to, from);
            applyToAllOpenProjects(project, to, from);
            StatusBarUtil.setStatusBarInfo(project, "Autoscroll to/from Source settings are saved and will be applied to all projects opened or created in future");
        } catch (Exception e) {
            // ignore...
        }
    }

    private void applyToAllOpenProjects(Project project, boolean to, boolean from) {
        // apply setting to all other open projects
        for (Project otherProject : ProjectManager.getInstance().getOpenProjects()) {
            if (otherProject != project) {
                try {
                    ProjectViewImpl otherProjectView = (ProjectViewImpl) ProjectView.getInstance(otherProject);
                    otherProjectView.setAutoscrollToSource(to, ProjectViewPane.ID);
                    SetAutoScrollFromSourceHack.setAutoScrollFromSource(otherProjectView, from);
                } catch (Exception e) {
                    // ignore
                }
            }
        }
    }
}
