package xyz.phanta.tconevo.coremod;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.function.Consumer;

public class TransformItemSensitiveModifiers implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Item-Sensitive Modifiers";
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("slimeknights.tconstruct.library.utils.ToolBuilder");
        // conarm function for armour building coincidentally has the exact same structure as the tcon one for tools
        collector.accept("c4.conarm.lib.tinkering.ArmorBuilder");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ToolBuilderTransformer(apiVersion, downstream);
    }

    private static class ToolBuilderTransformer extends ClassVisitor {

        public ToolBuilderTransformer(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            switch (name) {
                case "tryModifyTool":
                case "tryModifyArmor":
                    return new TryModifyToolTransformer(api, super.visitMethod(access, name, desc, signature, exceptions));
                default:
                    return super.visitMethod(access, name, desc, signature, exceptions);
            }
        }

    }

    private static class TryModifyToolTransformer extends MethodVisitor {

        private int state = -2;

        public TryModifyToolTransformer(int api, MethodVisitor mv) {
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
                                "xyz/phanta/tconevo/handler/ItemSensitiveModificationHandler",
                                "canApply",
                                "(Lslimeknights/tconstruct/library/modifiers/IModifier;Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;Lslimeknights/mantle/util/RecipeMatch$Match;)Z",
                                false);
                        return;
                    case "apply":
                        super.visitVarInsn(Opcodes.ALOAD, state);
                        super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                "xyz/phanta/tconevo/handler/ItemSensitiveModificationHandler",
                                "apply",
                                "(Lslimeknights/tconstruct/library/modifiers/IModifier;Lnet/minecraft/item/ItemStack;Lslimeknights/mantle/util/RecipeMatch$Match;)V",
                                false);
                        return;
                }
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

    }

}
