package xyz.phanta.tconevo.coremod.transform;

import org.objectweb.asm.*;
import xyz.phanta.tconevo.coremod.TconEvoClassTransformer;

import java.util.function.Consumer;

public class TransformMaterialisConArmCrash implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Materialis ConArm Crash Fix";
    }

    @Override
    public int getWriteFlags() {
        return ClassWriter.COMPUTE_FRAMES;
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("com.rcx.materialis.modules.ModuleConarm");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerModuleConarm(apiVersion, downstream);
    }

    private static class ClassTransformerModuleConarm extends ClassVisitor {

        public ClassTransformerModuleConarm(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("generateArmorStats")) {
                return new MethodTransformerGenerateArmorStats(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerGenerateArmorStats extends MethodVisitor {

        public MethodTransformerGenerateArmorStats(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            super.visitVarInsn(opcode, var);
            if (opcode == Opcodes.ASTORE) {
                super.visitVarInsn(Opcodes.ALOAD, var);
                Label elseLabel = new Label();
                super.visitJumpInsn(Opcodes.IFNONNULL, elseLabel);
                super.visitInsn(Opcodes.RETURN);
                super.visitLabel(elseLabel);
            }
        }

    }

}
