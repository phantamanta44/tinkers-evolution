package xyz.phanta.tconevo.coremod;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.function.Consumer;

// the method ModelHelper::getBakedModelForItem fails for models with built-in renderers
// this is ridiculous, so this workaround replaces all calls with a direct call to retrieve the model
public class TransformUseJeiFancyRender implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Use JEI Fancy Render";
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("mezz.jei.render.IngredientListBatchRenderer");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerIngredientListBatchRenderer(apiVersion, downstream);
    }

    private static class ClassTransformerIngredientListBatchRenderer extends ClassVisitor {

        public ClassTransformerIngredientListBatchRenderer(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("set")
                    && desc.equals("(Lmezz/jei/render/IngredientListSlot;Lmezz/jei/gui/ingredients/IIngredientListElement;)V")) {
                return new MethodTransformerSet(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerSet extends MethodVisitor {

        private int modelVar = -1;
        private int state = 0;

        public MethodTransformerSet(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            super.visitVarInsn(opcode, var);
            if (state == 0 && opcode == Opcodes.ALOAD) {
                // capture the model local that isBuiltInRenderer gets called on
                // hopefully nobody else rewrites this code drastically
                modelVar = var;
            }
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (state == 0 && opcode == Opcodes.INVOKEINTERFACE
                    && owner.equals("net/minecraft/client/renderer/block/model/IBakedModel")
                    && name.equals("isBuiltInRenderer")) {
                state = 1;
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

        @Override
        public void visitJumpInsn(int opcode, Label label) {
            if (state == 1 && opcode == Opcodes.IFNE) {
                state = 2;
                super.visitJumpInsn(Opcodes.IFNE, label);
                if (modelVar != -1) {
                    super.visitVarInsn(Opcodes.ALOAD, modelVar);
                    super.visitTypeInsn(Opcodes.INSTANCEOF, "xyz/phanta/tconevo/client/util/CodeChickenModel");
                    super.visitJumpInsn(Opcodes.IFNE, label);
                }
            } else {
                super.visitJumpInsn(opcode, label);
            }
        }

    }

}
