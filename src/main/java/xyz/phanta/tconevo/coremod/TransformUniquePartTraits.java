package xyz.phanta.tconevo.coremod;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.function.Consumer;

public class TransformUniquePartTraits implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Part Type Trait Uniqueness";
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("slimeknights.tconstruct.library.tinkering.PartMaterialType");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerPartMaterialType(apiVersion, downstream);
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
