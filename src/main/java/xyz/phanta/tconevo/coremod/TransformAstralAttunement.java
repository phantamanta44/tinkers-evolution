package xyz.phanta.tconevo.coremod;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.function.Consumer;

// astral sorcery's attunement altar uses a bunch of hardcoded logic to deal with infusion recipes
// this hack attempts to get around that by intercepting method calls that assume the attunement item is a crystal
public class TransformAstralAttunement implements TconEvoClassTransformer.Transform {

    private static final String TYPE_ROCK_CRYSTAL_BASE = "hellfirepvp/astralsorcery/common/item/crystal/base/ItemRockCrystalBase";

    @Override
    public String getName() {
        return "Astral Attunement Workaround";
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("hellfirepvp.astralsorcery.common.tile.TileAttunementAltar");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerTileAttunementAltar(apiVersion, downstream);
    }

    private static class ClassTransformerTileAttunementAltar extends ClassVisitor {

        public ClassTransformerTileAttunementAltar(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("func_73660_a") || name.equals("update")) {
                return new MethodTransformerUpdate(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerUpdate extends MethodVisitor {

        private int untunedStackVar = -1;
        private boolean gotTunedItem = false;

        public MethodTransformerUpdate(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (opcode != Opcodes.CHECKCAST || !type.equals(TYPE_ROCK_CRYSTAL_BASE)) {
                super.visitTypeInsn(opcode, type);
            }
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            if (!gotTunedItem && opcode == Opcodes.ALOAD) {
                untunedStackVar = var; // just capture all aload until we see the call to getTunedItemVariant
            }
            super.visitVarInsn(opcode, var);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            switch (opcode) {
                case Opcodes.INVOKEVIRTUAL:
                    if (untunedStackVar != -1 && owner.equals(TYPE_ROCK_CRYSTAL_BASE) && name.equals("getTunedItemVariant")) {
                        // this may cause a type error if the caller assumes the result is ItemRockCrystalBase
                        // but the default attunement altar impl only assumes that it's an Item
                        // passes both the item and stack because it's easier to not have to rewrite the getItem call
                        super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                "xyz/phanta/tconevo/integration/astralsorcery/AttunementGenerifyHandler",
                                "getTunedItemVariant",
                                "(Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;",
                                false);
                        gotTunedItem = true;
                        return;
                    }
                    break;
                case Opcodes.INVOKESPECIAL:
                    if (gotTunedItem && owner.equals("net/minecraft/item/ItemStack") && name.equals("<init>")) {
                        super.visitMethodInsn(Opcodes.INVOKESPECIAL, "net/minecraft/item/ItemStack", "<init>", desc, false);
                        super.visitVarInsn(Opcodes.ALOAD, untunedStackVar);
                        super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                "xyz/phanta/tconevo/integration/astralsorcery/AttunementGenerifyHandler",
                                "copyUnattunedStackProperties",
                                "(Lnet/minecraft/item/ItemStack;Lnet/minecraft/item/ItemStack;)Lnet/minecraft/item/ItemStack;",
                                false);
                        gotTunedItem = false;
                        untunedStackVar = -1;
                        return;
                    }
                    break;
                case Opcodes.INVOKESTATIC:
                    switch (owner) {
                        case "hellfirepvp/astralsorcery/common/item/crystal/base/ItemTunedCrystalBase":
                            if (name.equals("applyMainConstellation")) {
                                super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                        "xyz/phanta/tconevo/integration/astralsorcery/AttunementGenerifyHandler",
                                        "applyMainConstellation",
                                        "(Lnet/minecraft/item/ItemStack;Lhellfirepvp/astralsorcery/common/constellation/IWeakConstellation;)V",
                                        false);
                                return;
                            }
                            break;
                        case "hellfirepvp/astralsorcery/common/item/crystal/CrystalProperties":
                            if (name.equals("applyCrystalProperties")) {
                                super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                        "xyz/phanta/tconevo/integration/astralsorcery/AttunementGenerifyHandler",
                                        "applyCrystalProperties",
                                        "(Lnet/minecraft/item/ItemStack;Lhellfirepvp/astralsorcery/common/item/crystal/CrystalProperties;)V",
                                        false);
                                return;
                            }
                            break;
                    }
                    break;
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

    }

}
