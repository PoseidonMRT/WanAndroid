package com.poseidon.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.commons.AdviceAdapter;

public class PluginAClassVisitor extends ClassVisitor {

    private String mClassName;

    public PluginAClassVisitor(ClassVisitor classVisitor, String className) {
        super(Opcodes.ASM7, classVisitor);
        mClassName = className;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor methodVisitor = super.visitMethod(access, name, descriptor, signature, exceptions);
        MethodVisitor newMethodVisitor = new AdviceAdapter(Opcodes.ASM7, methodVisitor, access, name, descriptor) {
            @Override
            protected void onMethodEnter() {
                System.out.println("onMethodEnter className:" + mClassName + "#" + getName());

                mv.visitLdcInsn("trace");
                mv.visitLdcInsn(mClassName + "---->" + getName() + " enter");
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);
                mv.visitInsn(POP);
                super.onMethodEnter();
            }

            @Override
            protected void onMethodExit(int opcode) {
                System.out.println("onMethodExit className:" + mClassName + "#" + getName());

                mv.visitLdcInsn("trace");
                mv.visitLdcInsn(mClassName + "---->" + getName() + " exit");
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "android/util/Log", "i", "(Ljava/lang/String;Ljava/lang/String;)I", false);

                mv.visitInsn(Opcodes.POP);
                super.onMethodExit(opcode);
            }
        };
        return newMethodVisitor;
    }
}