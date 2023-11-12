package xyz.phanta.tconevo.coremod.transform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import xyz.phanta.tconevo.coremod.TconEvoClassTransformer;

import java.util.function.Consumer;

public class TransformBreakUnbreakable implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Break Unbreakables";
    }

    @Override
    public int getWriteFlags() {
        return ClassWriter.COMPUTE_MAXS;
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("net.minecraftforge.common.ForgeHooks");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerForgeHooks(apiVersion, downstream);
    }

    private static class ClassTransformerForgeHooks extends ClassVisitor {

        ClassTransformerForgeHooks(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("blockStrength")) {
                return new MethodTransformerBlockStrength(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerBlockStrength extends MethodVisitor {

        MethodTransformerBlockStrength(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            super.visitMethodInsn(opcode, owner, name, desc, itf);
            if (opcode == Opcodes.INVOKEINTERFACE
                    && owner.equals("net/minecraft/block/state/IBlockState") && name.equals("getBlockHardness")
                    && desc.equals("(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F")) {
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitVarInsn(Opcodes.ALOAD, 1);
                super.visitVarInsn(Opcodes.ALOAD, 2);
                super.visitVarInsn(Opcodes.ALOAD, 3);
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "xyz/phanta/tconevo/handler/BlockPropCoreHooks", "getBlockStrength",
                        "(FLnet/minecraft/block/state/IBlockState;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F",
                        false);
            }
        }

    }

}
