package xyz.phanta.tconevo.coremod;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.function.Consumer;

public class TransformFixArmourDamage implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Fix Armour Damage";
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

        MethodTransformerApplyArmor(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (opcode == Opcodes.INVOKEVIRTUAL && (name.equals("func_77972_a") || name.equals("damageItem"))
                    && owner.equals("net/minecraft/item/ItemStack") && desc.equals("(ILnet/minecraft/entity/EntityLivingBase;)V")) {
                super.visitMethodInsn(Opcodes.INVOKESTATIC, "xyz/phanta/tconevo/handler/ArmourDamageCoreHooks",
                        "damageItem", "(Lnet/minecraft/item/ItemStack;ILnet/minecraft/entity/EntityLivingBase;)V", false);
            } else {
                super.visitMethodInsn(opcode, owner, name, desc, itf);
            }
        }

    }

}
