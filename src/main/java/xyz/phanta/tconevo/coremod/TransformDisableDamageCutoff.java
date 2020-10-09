package xyz.phanta.tconevo.coremod;

import org.objectweb.asm.*;

import java.util.function.Consumer;

public class TransformDisableDamageCutoff implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Disable Damage Cutoff";
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("slimeknights.tconstruct.library.utils.ToolHelper");
    }

    @Override
    public int getWriteFlags() {
        return ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS;
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerToolHelper(apiVersion, downstream);
    }

    private static class ClassTransformerToolHelper extends ClassVisitor {

        ClassTransformerToolHelper(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("calcCutoffDamage") && desc.equals("(FF)F")) {
                return new MethodTransformerCalcCutoffDamage(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerCalcCutoffDamage extends MethodVisitor {

        private boolean ready = true;

        MethodTransformerCalcCutoffDamage(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitLabel(Label label) {
            super.visitLabel(label);
            if (ready) {
                ready = false;
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "xyz/phanta/tconevo/handler/DamageCutoffCoreHooks",
                        "shouldIgnoreCutoff", "()Z", false);
                Label noIgnore = new Label();
                super.visitJumpInsn(Opcodes.IFEQ, noIgnore);
                super.visitVarInsn(Opcodes.FLOAD, 0);
                super.visitInsn(Opcodes.FRETURN);
                super.visitLabel(noIgnore);
            }
        }

    }

}
