package xyz.phanta.tconevo.coremod;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

public class ClassTransformerUniquePartTraits implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] code) {
        if (transformedName.equals("slimeknights.tconstruct.library.tinkering.PartMaterialType")) {
            System.out.println("[tconevo] Injecting part type trait uniqueness check...");
            ClassReader reader = new ClassReader(code);
            ClassWriter writer = new ClassWriter(reader, 0);
            reader.accept(new ClassTransformerPartMaterialType(Opcodes.ASM5, writer), 0);
            return writer.toByteArray();
        }
        return code;
    }

    private static class ClassTransformerPartMaterialType extends ClassVisitor {

        public ClassTransformerPartMaterialType(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("getApplicableTraitsForMaterial")) {
                return new MethodTransformerGetApplicableTraits(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerGetApplicableTraits extends MethodVisitor {

        public MethodTransformerGetApplicableTraits(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == Opcodes.ARETURN) {
                super.visitMethodInsn(Opcodes.INVOKESTATIC,
                        "com/google/common/collect/Sets",
                        "newHashSet", "(Ljava/lang/Iterable;)Ljava/util/HashSet;", false);
            }
            super.visitInsn(opcode);
        }

    }

}
