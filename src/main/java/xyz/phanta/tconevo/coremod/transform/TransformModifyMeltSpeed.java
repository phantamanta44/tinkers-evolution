package xyz.phanta.tconevo.coremod.transform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import xyz.phanta.tconevo.coremod.TconEvoClassTransformer;

import java.util.function.Consumer;

public class TransformModifyMeltSpeed implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "Modify Melt Speed";
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("slimeknights.tconstruct.smeltery.tileentity.TileHeatingStructure");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerTileHeatingStructure(apiVersion, downstream);
    }

    private static class ClassTransformerTileHeatingStructure extends ClassVisitor {

        public ClassTransformerTileHeatingStructure(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("heatSlot") && desc.equals("(I)I")) {
                return new MethodTransformerHeatSlot(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerHeatSlot extends MethodVisitor {

        public MethodTransformerHeatSlot(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitInsn(int opcode) {
            if (opcode == Opcodes.IRETURN) {
                super.visitMethodInsn(Opcodes.INVOKESTATIC,
                        "xyz/phanta/tconevo/handler/MeltSpeedCoreHooks", "modifyMeltRate", "(I)I", false);
            }
            super.visitInsn(opcode);
        }

    }

}
