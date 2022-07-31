package com.poseidon.plugin;

import com.android.build.api.instrumentation.FramesComputationMode;
import com.android.build.api.instrumentation.InstrumentationParameters;
import com.android.build.api.instrumentation.InstrumentationScope;
import com.android.build.api.variant.AndroidComponentsExtension;
import com.android.build.api.variant.Variant;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class PluginA implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("this is A plugin output start");
        AndroidComponentsExtension androidComponents = (AndroidComponentsExtension) project.getExtensions().getByType(AndroidComponentsExtension.class);
        androidComponents.onVariants(androidComponents.selector(), new Action<Variant>() {
            @Override
            public void execute(Variant variant) {
                variant.getInstrumentation().transformClassesWith(PluginATransform.class, InstrumentationScope.ALL, new Function1<InstrumentationParameters.None, Unit>() {
                    @Override
                    public Unit invoke(InstrumentationParameters.None none) {

                        return Unit.INSTANCE;
                    }
                });
                variant.getInstrumentation().setAsmFramesComputationMode(FramesComputationMode.COMPUTE_FRAMES_FOR_ALL_CLASSES);
            }
        });
        System.out.println("this is A plugin output end");
    }
}
