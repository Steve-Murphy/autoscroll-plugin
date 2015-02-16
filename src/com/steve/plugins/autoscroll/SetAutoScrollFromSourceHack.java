package com.steve.plugins.autoscroll;

import com.intellij.ide.projectView.impl.ProjectViewImpl;
import com.intellij.ui.AutoScrollFromSourceHandler;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

// In emergency, BREAK GLASS.
public class SetAutoScrollFromSourceHack {
    public static void setAutoScrollFromSource(ProjectViewImpl projectView, boolean value) {
        try {
            for (Field field : ProjectViewImpl.class.getDeclaredFields()) {
                if (AutoScrollFromSourceHandler.class.isAssignableFrom(field.getType())) {
                    field.setAccessible(true);      // shouldn't ever do this...
                    AutoScrollFromSourceHandler autoScrollFromSourceHandler = (AutoScrollFromSourceHandler) field.get(projectView);
                    for (Method method : AutoScrollFromSourceHandler.class.getDeclaredMethods()) {
                        if ("setAutoScrollEnabled".equals(method.getName())) {
                            method.setAccessible(true);     // shouldn't do this either!
                            method.invoke(autoScrollFromSourceHandler, value);
                            break;
                        }
                    }

                }
            }
        } catch (Exception e) {
            // oops!
        }
    }
}
