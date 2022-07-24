package com.poseidon.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class PluginA implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("this is A plugin output");
    }
}
