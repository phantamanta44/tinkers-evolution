package xyz.phanta.tconevo.coremod;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class TransformThaumVisDiscount implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Thaumcraft Vis Discount";
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("thaumcraft.common.items.casters.CasterManager");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerCasterManager(apiVersion, downstream);
    }

    private static class ClassTransformerCasterManager extends ClassVisitor {

        public ClassTransformerCasterManager(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("getTotalVisDiscount") && desc.equals("(Lnet/minecraft/entity/player/EntityPlayer;)F")) {
                return new MethodTransformerGetTotalVisDiscount(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerGetTotalVisDiscount extends MethodVisitor {

        @Nullable
        private Label elseLabel = null;

        public MethodTransformerGetTotalVisDiscount(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitJumpInsn(int opcode, Label label) {
            super.visitJumpInsn(opcode, label);
            if (elseLabel == null && opcode == Opcodes.IFNONNULL) {
                elseLabel = label;
            }
        }

        @Override
        public void visitLabel(Label label) {
            super.visitLabel(label);
            if (label.equals(elseLabel)) {
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitMethodInsn(Opcodes.INVOKESTATIC,
                        "xyz/phanta/tconevo/integration/thaumcraft/VisDiscountCoreHooks", "getVisDiscount",
                        "(Lnet/minecraft/entity/player/EntityPlayer;)I",
                        false);
                super.visitVarInsn(Opcodes.ISTORE, 1);
            }
        }

    }

}
