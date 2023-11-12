package xyz.phanta.tconevo.coremod.transform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import xyz.phanta.tconevo.coremod.TconEvoClassTransformer;

import java.util.function.Consumer;

public class TransformImprovedToolBuilding implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Improved Tool Building";
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("slimeknights.tconstruct.library.utils.ToolBuilder");
        // conarm function for armour building coincidentally has the exact same structure as the tcon one for tools
        collector.accept("c4.conarm.lib.tinkering.ArmorBuilder");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerToolBuilder(apiVersion, downstream);
    }

    private static class ClassTransformerToolBuilder extends ClassVisitor {

        public ClassTransformerToolBuilder(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            switch (name) {
                case "tryModifyTool":
                case "tryModifyArmor":
                    return new MethodTransformerTryModifyTool(api, super.visitMethod(access, name, desc, signature, exceptions));
                case "rebuildTool":
                    return new MethodTransformerRebuildTool(api, super.visitMethod(access, name, desc, signature, exceptions));
                default:
                    return super.visitMethod(access, name, desc, signature, exceptions);
            }
        }

    }

    // deals with item-sensitive modifiers
    private static class MethodTransformerTryModifyTool extends MethodVisitor {

        private int state = -2;

        public MethodTransformerTryModifyTool(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (state == -2 && opcode == Opcodes.CHECKCAST && type.equals("slimeknights/mantle/util/RecipeMatch$Match")) {
                state = -1;
            }
            super.visitTypeInsn(opcode, type);
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            if (state == -1 && opcode == Opcodes.ASTORE) {
                state = var; // we've seen a cast to RecipeMatch.Match, so this astore must refer to the match var
            }
            super.visitVarInsn(opcode, var);
        }

        @Override
        public void visitLineNumber(int line, Label start) {
            if (state == -1) {
                state = -2; // if a checkcast isn't followed by an astore, abort
            }
            super.visitLineNumber(line, start);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (state > 0 && itf && opcode == Opcodes.INVOKEINTERFACE
                    && owner.equals("slimeknights/tconstruct/library/modifiers/IModifier")) {
                switch (name) {
                    case "canApply":
                        super.visitVarInsn(Opcodes.ALOAD, state);
                        super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                "xyz/phanta/tconevo/handler/ItemSensitiveModificationCoreHooks",
                                "canApply",
                                "(Lslimeknights/tconstruct/library/modifiers/IModifier;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lslimeknights/mantle/util/RecipeMatch$Match;)Z",
                                false);
                        return;
                    case "apply":
                        super.visitVarInsn(Opcodes.ALOAD, state);
                        super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                "xyz/phanta/tconevo/handler/ItemSensitiveModificationCoreHooks",
                                "apply",
                                "(Lslimeknights/tconstruct/library/modifiers/IModifier;Lnet/minecraft/item/ItemStack;Lslimeknights/mantle/util/RecipeMatch$Match;)V",
                                false);
                        return;
                }
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

    }

    // deals with stripping extra data from rebuilt tools
    private static class MethodTransformerRebuildTool extends MethodVisitor {

        private int lastVar = -1;
        private boolean go = false;

        public MethodTransformerRebuildTool(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            if (opcode == Opcodes.ALOAD) {
                lastVar = var; // assume the last variable loaded is the tag
            }
            super.visitVarInsn(opcode, var);
        }

        @Override
        public void visitLdcInsn(Object cst) {
            if (lastVar != -1 && cst instanceof String && cst.equals("EnchantEffect")) {
                go = true;
            }
            super.visitLdcInsn(cst);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
            if (go && opcode == Opcodes.INVOKEVIRTUAL
                    && (name.equals("func_82580_o") || name.equals("removeTag")) && desc.equals("(Ljava/lang/String;)V")) {
                super.visitVarInsn(Opcodes.ALOAD, lastVar);
                super.visitLdcInsn("Unbreakable");
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, owner, name, "(Ljava/lang/String;)V", false);
            }
        }

        @Override
        public void visitLineNumber(int line, Label start) {
            go = false; // better safe than sorry
            super.visitLineNumber(line, start);
        }

    }

}
