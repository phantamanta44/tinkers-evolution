package xyz.phanta.tconevo.coremod;

import org.objectweb.asm.*;

import java.util.function.Consumer;

public class TransformFixArmourDamage implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Fix Armour Damage";
    }

    @Override
    public int getWriteFlags() {
        return ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS;
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("net.minecraftforge.common.ISpecialArmor$ArmorProperties");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerArmorProperties(apiVersion, downstream);
    }

    private static class ClassTransformerArmorProperties extends ClassVisitor {

        ClassTransformerArmorProperties(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("applyArmor") && desc.equals(
                    "(Lnet/minecraft/entity/EntityLivingBase;Lnet/minecraft/util/NonNullList;Lnet/minecraft/util/DamageSource;D)F")) {
                return new MethodTransformerApplyArmor(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerApplyArmor extends MethodVisitor {

        private final VarTracker vars = new VarTracker(5);

        MethodTransformerApplyArmor(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitVarInsn(int opcode, int var) {
            super.visitVarInsn(opcode, vars.map(opcode, var));
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (opcode == Opcodes.INVOKEVIRTUAL && (name.equals("func_77972_a") || name.equals("damageItem"))
                    && owner.equals("net/minecraft/item/ItemStack") && desc.equals("(ILnet/minecraft/entity/EntityLivingBase;)V")) {
                // pop args off stack and store in local vars
                int varItemStack = vars.create(), varDamage = vars.create(), varWielder = vars.create();
                super.visitVarInsn(Opcodes.ASTORE, varWielder);
                super.visitVarInsn(Opcodes.ISTORE, varDamage);
                super.visitVarInsn(Opcodes.ASTORE, varItemStack);

                // load args, call hook
                super.visitVarInsn(Opcodes.ALOAD, varItemStack);
                super.visitVarInsn(Opcodes.ILOAD, varDamage);
                super.visitVarInsn(Opcodes.ALOAD, varWielder);
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "xyz/phanta/tconevo/handler/ArmourDamageCoreHooks",
                        "shouldDamageItem", "(Lnet/minecraft/item/ItemStack;ILnet/minecraft/entity/EntityLivingBase;)Z", false);

                // if hook returned false, cancel the damage; otherwise, load args and call damage function
                Label cancelDamage = new Label();
                super.visitJumpInsn(Opcodes.IFEQ, cancelDamage);
                super.visitVarInsn(Opcodes.ALOAD, varItemStack);
                super.visitVarInsn(Opcodes.ILOAD, varDamage);
                super.visitVarInsn(Opcodes.ALOAD, varWielder);
                super.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "net/minecraft/item/ItemStack",
                        name, "(ILnet/minecraft/entity/EntityLivingBase;)V", false);
                super.visitLabel(cancelDamage);
            } else {
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        }

    }

}
