package xyz.phanta.tconevo.coremod.transform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;
import xyz.phanta.tconevo.coremod.TconEvoClassTransformer;
import xyz.phanta.tconevo.coremod.TconEvoCoreMod;
import xyz.phanta.tconevo.coremod.util.InsnMatcher;
import xyz.phanta.tconevo.coremod.util.InsnWriter;
import xyz.phanta.tconevo.coremod.util.MethodRewriter;

import java.util.Objects;
import java.util.function.Consumer;

// astral sorcery's attunement altar uses a bunch of hardcoded logic to deal with infusion recipes
// this hack attempts to get around that by injecting our own crafting logic before the AS logic for crystal attunement
public class TransformAstralAttunement implements TconEvoClassTransformer.Transform {

    private static final String TYPE_ATT_ALTAR = "hellfirepvp/astralsorcery/common/tile/TileAttunementAltar";

    @Override
    public String getName() {
        return "Astral Attunement Workaround";
    }

    @Override
    public int getWriteFlags() {
        return ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS;
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
                return new MethodRewriterUpdate(api, super.visitMethod(access, name, desc, signature, exceptions),
                        access, name, desc, signature, exceptions);
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodRewriterUpdate extends MethodRewriter {

        private static final InsnMatcher MATCH_CRAFTING_CHECK = InsnMatcher.sequence(
                InsnMatcher.varInsn(Opcodes.ALOAD, 0),
                InsnMatcher.fieldInsn(Opcodes.GETFIELD, TYPE_ATT_ALTAR, "serverSyncAttTick", "I"),
                InsnMatcher.intInsn(Opcodes.SIPUSH, 500),
                InsnMatcher.anyInsn(Opcodes.IF_ICMPLT));

        private static final InsnMatcher MATCH_GET_THROWER = InsnMatcher.sequence(
                InsnMatcher.varInsn(Opcodes.ALOAD, 0),
                InsnMatcher.fieldInsn(Opcodes.GETFIELD,
                        TYPE_ATT_ALTAR, "activeEntity", "Lnet/minecraft/entity/Entity;"),
                InsnMatcher.typeInsn(Opcodes.CHECKCAST, "net/minecraft/entity/item/EntityItem"),
                InsnMatcher.methodInsn(Opcodes.INVOKEVIRTUAL, // getThrower
                        "net/minecraft/entity/item/EntityItem", "func_145800_j", "()Ljava/lang/String;"),
                InsnMatcher.anyInsn(Opcodes.ASTORE));

        private static final InsnMatcher MATCH_DROP_ITEM = InsnMatcher.sequence(
                InsnMatcher.varInsn(Opcodes.ALOAD, 0),
                InsnMatcher.fieldInsn(Opcodes.GETFIELD, // world
                        TYPE_ATT_ALTAR, "field_145850_b", "Lnet/minecraft/world/World;"),
                InsnMatcher.anyInsn(Opcodes.ALOAD),
                InsnMatcher.methodInsn(Opcodes.INVOKEVIRTUAL,
                        "hellfirepvp/astralsorcery/common/util/data/Vector3", "getX", "()D"),
                InsnMatcher.anyInsn(Opcodes.ALOAD),
                InsnMatcher.methodInsn(Opcodes.INVOKEVIRTUAL,
                        "hellfirepvp/astralsorcery/common/util/data/Vector3", "getY", "()D"),
                InsnMatcher.anyInsn(Opcodes.ALOAD),
                InsnMatcher.methodInsn(Opcodes.INVOKEVIRTUAL,
                        "hellfirepvp/astralsorcery/common/util/data/Vector3", "getZ", "()D"),
                InsnMatcher.anyInsn(Opcodes.ALOAD),
                InsnMatcher.methodInsn(Opcodes.INVOKESTATIC,
                        "hellfirepvp/astralsorcery/common/util/ItemUtils", "dropItem",
                        "(Lnet/minecraft/world/World;DDDLnet/minecraft/item/ItemStack;)" +
                                "Lnet/minecraft/entity/item/EntityItem;"));

        public MethodRewriterUpdate(int api, MethodVisitor downstream,
                                    int access, String name, String desc, String signature, String[] exceptions) {
            super(api, downstream, access, name, desc, signature, exceptions);
        }

        @Override
        protected void rewrite(MethodNode method) {
            InsnMatcher.Match mCraftingCheck = MATCH_CRAFTING_CHECK.find(method.instructions.getFirst());
            if (mCraftingCheck == null) {
                TconEvoCoreMod.LOGGER.warn("Failed to find attunement completion check!");
                return;
            }

            InsnMatcher.Match mThrower = MATCH_GET_THROWER.find(mCraftingCheck.tail);
            if (mThrower == null) {
                TconEvoCoreMod.LOGGER.warn("Failed to find attunement item thrower check!");
                return;
            }
            InsnMatcher.Match mDrop = MATCH_DROP_ITEM.find(mThrower.tail);
            if (mDrop == null) {
                TconEvoCoreMod.LOGGER.warn("Failed to find attunement result drop!");
                return;
            }
            int resultVar = ((VarInsnNode) Objects.requireNonNull(walkForwards(mDrop.start, 8))).var;

            LabelNode successLabel = new LabelNode();
            method.instructions.insertBefore(mCraftingCheck.tail, getInjectedCode(resultVar, successLabel));
            method.instructions.insertBefore(mThrower.start, successLabel);
        }

        private static InsnList getInjectedCode(int resultVar, LabelNode successLabel) {
            return new InsnWriter()
                    .aload(0)
                    .getfield(TYPE_ATT_ALTAR, "activeEntity", "Lnet/minecraft/entity/Entity;")
                    .checkcast("net/minecraft/entity/item/EntityItem")
                    .invokevirtual( // getItem
                            "net/minecraft/entity/item/EntityItem", "func_92059_d", "()Lnet/minecraft/item/ItemStack;")
                    .aload(0)
                    .getfield(TYPE_ATT_ALTAR, "activeFound",
                            "Lhellfirepvp/astralsorcery/common/constellation/IConstellation;")
                    .invokestatic(
                            "xyz/phanta/tconevo/integration/astralsorcery/AttunementGenerifyCoreHooks", "tryAttuneItem",
                            "(Lnet/minecraft/item/ItemStack;" +
                                    "Lhellfirepvp/astralsorcery/common/constellation/IConstellation;)" +
                                    "Lnet/minecraft/item/ItemStack;")
                    .astore(resultVar)
                    .aload(resultVar)
                    .ifnonnull(successLabel)
                    .asList();
        }

    }

}
