package com.poseidon.plugin;

import com.android.build.api.instrumentation.*;

import org.objectweb.asm.ClassVisitor;

public abstract class PluginATransform implements AsmClassVisitorFactory<InstrumentationParameters.None> {
    @Override
    public ClassVisitor createClassVisitor(ClassContext classContext, ClassVisitor classVisitor) {
        return new PluginAClassVisitor(classVisitor, classContext.getCurrentClassData().getClassName());
    }

    @Override
    public boolean isInstrumentable(ClassData classData) {
        return (classData.getClassName().contains("com.poseidon"));
    }
}