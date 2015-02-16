package com.steve.plugins.autoscroll;

import com.intellij.ide.projectView.ProjectView;
import com.intellij.ide.projectView.impl.ProjectViewImpl;
import com.intellij.ide.projectView.impl.ProjectViewPane;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.project.ProjectManagerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
    name = "autoscroll",
    storages = {
        @Storage(id = "autoscroll", file = StoragePathMacros.APP_CONFIG + "/autoscroll.xml")
    }
)
public class AutoScrollComponent implements ApplicationComponent, PersistentStateComponent<AutoScrollComponent.State> {

    public static class State {
        public boolean remember;
        public boolean autoscrollToSource;
        public boolean autoscrollFromSource;
    }

    State state;

    public void setState(boolean to, boolean from) {
        if (state == null) {
            state = new State();
        }
        state.remember = true;
        state.autoscrollToSource = to;
        state.autoscrollFromSource = from;
    }

    @Override
    public void initComponent() {
        // listen for when new projects are opened
        ProjectManager.getInstance().addProjectManagerListener(new ProjectManagerAdapter() {
            @Override
            public void projectOpened(Project project) {
                try {
                    if (state != null && state.remember) {
                        ProjectViewImpl projectView = (ProjectViewImpl) ProjectView.getInstance(project);
                        projectView.setAutoscrollToSource(state.autoscrollToSource, ProjectViewPane.ID);
                        projectView.setAutoscrollFromSource(state.autoscrollFromSource, ProjectViewPane.ID);
                        projectView.getActions(true);
                    }
                } catch (Exception e) {
                    // ignore
                }
            }
        });
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return getClass().getSimpleName();
    }

    @Nullable
    @Override
    public State getState() {
        return state;
    }

    @Override
    public void loadState(State state) {
        this.state = state;
    }
}
