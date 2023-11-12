package xyz.phanta.tconevo.coremod.transform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import xyz.phanta.tconevo.coremod.TconEvoClassTransformer;

import java.util.function.Consumer;

public class TransformThaumInfusionEnchantment implements TconEvoClassTransformer.Transform {

    private static final String TYPE_INF_ENCH_RECIPE = "thaumcraft.common.lib.crafting.InfusionEnchantmentRecipe";
    private static final String TYPE_TOOL_EVENTS = "thaumcraft.common.lib.events.ToolEvents";

    @Override
    public String getName() {
        return "Thaumcraft Infusion Enchantment";
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept(TYPE_INF_ENCH_RECIPE);
        collector.accept(TYPE_TOOL_EVENTS);
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        switch (className) {
            case TYPE_INF_ENCH_RECIPE:
                return new ClassTransformerInfusionEnchantmentRecipe(apiVersion, downstream);
            case TYPE_TOOL_EVENTS:
                return new ClassTransformerToolEvents(apiVersion, downstream);
            default:
                return downstream;
        }
    }

    private static class ClassTransformerInfusionEnchantmentRecipe extends ClassVisitor {

        public ClassTransformerInfusionEnchantmentRecipe(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("matches")) {
                return new MethodTransformerMatches(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    // this is liable to crash if someone else injects something that assumes the item is ItemTool
    // hopefully, nobody does that :)
    private static class MethodTransformerMatches extends MethodVisitor {

        private boolean seenInstanceOf = false;

        public MethodTransformerMatches(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (type.equals("net/minecraft/item/ItemTool")) {
                switch (opcode) {
                    case Opcodes.INSTANCEOF:
                        seenInstanceOf = true;
                        super.visitInsn(Opcodes.POP); // drop the instanceof and replace it with constant true
                        super.visitInsn(Opcodes.ICONST_1);
                        return;
                    case Opcodes.CHECKCAST:
                        if (seenInstanceOf) {
                            return; // drop the cast to ItemTool
                        }
                        break;
                }
            } else {
                seenInstanceOf = false; // probably isn't strictly necessary
            }
            super.visitTypeInsn(opcode, type);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (name.equals("getToolClasses") && owner.equals("net/minecraft/item/ItemTool")) {
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, // why did they even need to check for instanceof ItemTool???
                        "net/minecraft/item/Item", "getToolClasses", "(Lnet/minecraft/item/ItemStack;)Ljava/util/Set;", false);
            } else {
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        }

    }

    private static class ClassTransformerToolEvents extends ClassVisitor {

        public ClassTransformerToolEvents(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            return new MethodTransformerToolEvents(api, super.visitMethod(access, name, desc, signature, exceptions), name);
        }

    }

    private static class MethodTransformerToolEvents extends MethodVisitor {

        private final boolean overwriteIsToolEffective;

        public MethodTransformerToolEvents(int api, MethodVisitor mv, String methodName) {
            super(api, mv);
            overwriteIsToolEffective = methodName.equals("harvestBlockEvent");
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (name.equals("damageItem") && owner.equals("net/minecraft/item/ItemStack")) {
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "xyz/phanta/tconevo/handler/TinkerToolPropCoreHooks",
                        "damageItem", "(Lnet/minecraft/item/ItemStack;ILnet/minecraft/entity/EntityLivingBase;)V", false);
            } else if (overwriteIsToolEffective && name.equals("isToolEffective") && owner.equals("net/minecraftforge/common/ForgeHooks")) {
                // thaumcraft calls ForgeHooks::isToolEffective to check if the tool is effective on the broken block for harvesting
                // however, the block is already gone by the time the event is posted, so this check always fails
                // this is bad, since it makes some infusion enchantments unusable with tinkers' tools
                // thus, we'll fix the thaumcraft bug using a class transformer :I
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraftforge/event/world/BlockEvent",
                        "getState", "()Lnet/minecraft/block/state/IBlockState;", false);
                // easier to just leave the extraneous args and pass them unused
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "xyz/phanta/tconevo/handler/TinkerToolPropCoreHooks",
                        "isToolEffective",
                        "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/item/ItemStack;Lnet/minecraft/block/state/IBlockState;)Z",
                        false);
            } else {
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        }

    }

}
