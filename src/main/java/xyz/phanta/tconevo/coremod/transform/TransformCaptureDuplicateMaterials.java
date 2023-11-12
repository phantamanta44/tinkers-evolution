package xyz.phanta.tconevo.coremod.transform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import xyz.phanta.tconevo.coremod.TconEvoClassTransformer;

import java.util.function.Consumer;

public class TransformCaptureDuplicateMaterials implements TconEvoClassTransformer.Transform {

    private static final String TYPE_TINKER_REGISTRY = "slimeknights/tconstruct/library/TinkerRegistry";

    @Override
    public String getName() {
        return "Capture Duplicate Materials";
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("slimeknights.tconstruct.library.TinkerRegistry");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerTinkerRegistry(apiVersion, downstream);
    }

    private static class ClassTransformerTinkerRegistry extends ClassVisitor {

        public ClassTransformerTinkerRegistry(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("addMaterial") && desc.equals("(Lslimeknights/tconstruct/library/materials/Material;)V")) {
                return new MethodTransformerAddMaterial(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerAddMaterial extends MethodVisitor {

        private int state = 0;

        public MethodTransformerAddMaterial(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            super.visitFieldInsn(opcode, owner, name, desc);
            if (state == 0 && opcode == Opcodes.GETSTATIC
                    && owner.equals(TYPE_TINKER_REGISTRY) && name.equals("materials")) {
                state = 1;
            }
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            switch (state) {
                case 1:
                    if (owner.equals("java/util/Map") && name.equals("containsKey")) {
                        state = 2;
                    }
                    break;
                case 2:
                    if (owner.equals(TYPE_TINKER_REGISTRY) && name.equals("error")) {
                        super.visitVarInsn(Opcodes.ALOAD, 0);
                        super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                "xyz/phanta/tconevo/handler/MaterialPropertyCoreHooks", "handleDuplicateMaterial",
                                "(Ljava/lang/String;[Ljava/lang/Object;Lslimeknights/tconstruct/library/materials/Material;)V",
                                false);
                        state = 3;
                        return;
                    }
                    break;
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

    }

}
