package xyz.phanta.tconevo.coremod.transform;

import org.objectweb.asm.*;
import xyz.phanta.tconevo.coremod.TconEvoClassTransformer;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class TransformFixDraconicJei implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Fix Draconic Evolution JEI";
    }

    @Override
    public int getWriteFlags() {
        return ClassWriter.COMPUTE_FRAMES;
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("com.brandon3055.draconicevolution.integration.jei.FusionRecipeCategory");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerFusionRecipeCategory(apiVersion, downstream);
    }

    private static class ClassTransformerFusionRecipeCategory extends ClassVisitor {

        public ClassTransformerFusionRecipeCategory(int api, ClassVisitor downstream) {
            super(api, downstream);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("setRecipe") && desc.equals("(Lmezz/jei/api/gui/IRecipeLayout;" +
                    "Lmezz/jei/api/recipe/IRecipeWrapper;" +
                    "Lmezz/jei/api/ingredients/IIngredients;)V")) {
                return new MethodTransformerSetRecipe(
                        api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerSetRecipe extends MethodVisitor {

        private enum State {
            INITIAL,
            SEEN_GET_RECIPE, GOT_RECIPE,
            SEEN_INST_CHECK, SEEN_BRANCH,
            DONE
        }

        private State state = State.INITIAL;
        private int recipeVar = -1;
        @Nullable
        private Label ifEndLabel = null;

        public MethodTransformerSetRecipe(int api, MethodVisitor downstream) {
            super(api, downstream);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            if (state == State.INITIAL && opcode == Opcodes.GETFIELD
                    && owner.equals("com/brandon3055/draconicevolution/integration/jei/FusionRecipeWrapper")
                    && name.equals("recipe")) {
                state = State.SEEN_GET_RECIPE;
            }
            super.visitFieldInsn(opcode, owner, name, desc);
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            if (state == State.SEEN_GET_RECIPE && opcode == Opcodes.ASTORE) {
                state = State.GOT_RECIPE;
                recipeVar = var;
            }
            super.visitVarInsn(opcode, var);
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (state == State.GOT_RECIPE && opcode == Opcodes.INSTANCEOF
                    && type.equals("com/brandon3055/draconicevolution/api/itemupgrade/FusionUpgradeRecipe")) {
                state = State.SEEN_INST_CHECK;
            }
            super.visitTypeInsn(opcode, type);
        }

        @Override
        public void visitJumpInsn(int opcode, Label label) {
            if (state == State.SEEN_INST_CHECK && opcode == Opcodes.IFEQ) {
                state = State.SEEN_BRANCH;
                ifEndLabel = label;
            }
            super.visitJumpInsn(opcode, label);
        }

        @Override
        public void visitLabel(Label label) {
            super.visitLabel(label);
            switch (state) {
                case SEEN_GET_RECIPE:
                    state = State.INITIAL;
                    break;
                case SEEN_INST_CHECK:
                    state = State.GOT_RECIPE;
                    break;
                case SEEN_BRANCH:
                    if (label == ifEndLabel) {
                        state = State.DONE;
                        super.visitVarInsn(Opcodes.ALOAD, 1);
                        super.visitVarInsn(Opcodes.ALOAD, recipeVar);
                        super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                "xyz/phanta/tconevo/integration/draconicevolution/client/DraconicJeiCoreHooks",
                                "fixFusionRecipe", "(Lmezz/jei/api/gui/IRecipeLayout;" +
                                        "Lcom/brandon3055/draconicevolution/api/fusioncrafting/IFusionRecipe;)V",
                                false);
                    }
                    break;
            }
        }

    }

}
