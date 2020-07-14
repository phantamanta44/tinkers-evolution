package xyz.phanta.tconevo.coremod;

import jdk.internal.org.objectweb.asm.Opcodes;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

import java.util.function.Consumer;

public class TransformCaptureMaterialProperties implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Overridden Material Property Inheritance";
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("slimeknights.tconstruct.library.materials.Material");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerMaterial(apiVersion, downstream);
    }

    private static class ClassTransformerMaterial extends ClassVisitor {

        public ClassTransformerMaterial(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("addTrait")
                    && desc.equals("(Lslimeknights/tconstruct/library/traits/ITrait;Ljava/lang/String;)Lslimeknights/tconstruct/library/materials/Material;")) {
                return new MethodTransformerAddTrait(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerAddTrait extends MethodVisitor {

        public MethodTransformerAddTrait(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (opcode == Opcodes.INVOKESTATIC && name.equals("checkMaterialTrait")
                    && owner.equals("slimeknights/tconstruct/library/TinkerRegistry")) {
                super.visitMethodInsn(Opcodes.INVOKESTATIC,
                        "xyz/phanta/tconevo/handler/MaterialPropertyCoreHooks", "addTrait",
                        "(Lslimeknights/tconstruct/library/materials/Material;Lslimeknights/tconstruct/library/traits/ITrait;Ljava/lang/String;)V",
                        false);
                super.visitVarInsn(Opcodes.ALOAD, 0);
                super.visitVarInsn(Opcodes.ALOAD, 1);
                super.visitVarInsn(Opcodes.ALOAD, 2);
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

    }

}
