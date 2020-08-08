package xyz.phanta.tconevo.coremod;

import org.objectweb.asm.*;

import java.util.function.Consumer;

public class TransformCustomMaterialRender implements TconEvoClassTransformer.Transform {

    private static final String CLASS_MATERIAL_MODEL_LOADER = "slimeknights.tconstruct.library.client.model.MaterialModelLoader";
    private static final String CLASS_TOOL_MODEL_LOADER = "slimeknights.tconstruct.library.client.model.ToolModelLoader";
    private static final String TYPE_MATERIAL_MODEL = "slimeknights/tconstruct/library/client/model/MaterialModel";
    private static final String TYPE_TOOL_MODEL = "slimeknights/tconstruct/library/client/model/ToolModel";

    @Override
    public String getName() {
        return "Custom Material Render";
    }

//    @Override
//    public int getWriteFlags() {
//        return ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS;
//    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept(CLASS_MATERIAL_MODEL_LOADER);
        collector.accept(CLASS_TOOL_MODEL_LOADER);
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerModelLoader(apiVersion, downstream, CLASS_MATERIAL_MODEL_LOADER.equals(className));
    }

    private static class ClassTransformerModelLoader extends ClassVisitor {

        private final boolean renderHalo;

        ClassTransformerModelLoader(int api, ClassVisitor cv, boolean renderHalo) {
            super(api, cv);
            this.renderHalo = renderHalo;
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("loadModel")
                    && desc.equals("(Lnet/minecraft/util/ResourceLocation;)Lnet/minecraftforge/client/model/IModel;")) {
                return new MethodTransformerLoadModel(
                        api, super.visitMethod(access, name, desc, signature, exceptions), renderHalo);
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerLoadModel extends MethodVisitor {

        private final boolean renderHalo;
        private boolean dropDup = false;

        MethodTransformerLoadModel(int api, MethodVisitor mv, boolean renderHalo) {
            super(api, mv);
            this.renderHalo = renderHalo;
        }

        @Override
        public void visitTypeInsn(int opcode, String type) {
            if (opcode == Opcodes.NEW && (type.equals(TYPE_MATERIAL_MODEL) || type.equals(TYPE_TOOL_MODEL))) {
                dropDup = true;
            } else {
                super.visitTypeInsn(opcode, type);
            }
        }

        @Override
        public void visitInsn(int opcode) {
            // javac emits DUP after NEW so the new instance can be consumed by the ctor invoc
            if (dropDup && opcode == Opcodes.DUP) {
                dropDup = false;
            } else {
                super.visitInsn(opcode);
            }
        }

        @Override
        public void visitLineNumber(int line, Label start) {
            super.visitLineNumber(line, start);
            dropDup = false; // just in case
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
                switch (owner) {
                    case TYPE_MATERIAL_MODEL:
                        super.visitInsn(renderHalo ? Opcodes.ICONST_1 : Opcodes.ICONST_0);
                        switch (desc) {
                            case "(Lcom/google/common/collect/ImmutableList;)V":
                                super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                        "xyz/phanta/tconevo/client/handler/CustomModelRenderCoreHooks", "createMaterialModel",
                                        "(Lcom/google/common/collect/ImmutableList;Z)L" + TYPE_MATERIAL_MODEL + ";", false);
                                break;
                            case "(Lcom/google/common/collect/ImmutableList;II)V":
                                super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                        "xyz/phanta/tconevo/client/handler/CustomModelRenderCoreHooks", "createMaterialModel",
                                        "(Lcom/google/common/collect/ImmutableList;IIZ)L" + TYPE_MATERIAL_MODEL + ";", false);
                                break;
                        }
                        return;
                    case TYPE_TOOL_MODEL:
                        // that's a lot of parameters
                        super.visitMethodInsn(Opcodes.INVOKESTATIC,
                                "xyz/phanta/tconevo/client/handler/CustomModelRenderCoreHooks", "createToolModel",
                                "(Lcom/google/common/collect/ImmutableList;Ljava/util/List;Ljava/util/List;[Ljava/lang/Float;" +
                                        "Lslimeknights/tconstruct/library/client/model/ModifierModel;Lcom/google/common/collect/ImmutableMap;" +
                                        "Lcom/google/common/collect/ImmutableList;Lslimeknights/tconstruct/library/client/model/format/AmmoPosition;)L" + TYPE_TOOL_MODEL + ";",
                                false);
                        return;
                }
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

    }

}
