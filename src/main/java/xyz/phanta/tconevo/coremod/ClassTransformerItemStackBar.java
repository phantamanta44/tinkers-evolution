package xyz.phanta.tconevo.coremod;

import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.*;

import javax.annotation.Nullable;

// adapted from Tinkers' MEMES MemeClassTransformer
public class ClassTransformerItemStackBar implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] code) {
        if (transformedName.equals("net.minecraft.client.renderer.RenderItem")) {
            System.out.println("[tconevo] Injecting item stack bar overlay handler...");
            ClassReader reader = new ClassReader(code);
            ClassWriter writer = new ClassWriter(reader, 0);
            reader.accept(new RenderItemTransformer(Opcodes.ASM5, writer), 0);
            return writer.toByteArray();
        }
        return code;
    }

    private static class RenderItemTransformer extends ClassVisitor {

        @Nullable
        private String className = null;

        RenderItemTransformer(int api, ClassVisitor cv) {
            super(api, cv);
        }

        @Override
        public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
            this.className = name;
            super.visit(version, access, name, signature, superName, interfaces);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            String mappedName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(className, name, desc);
            if ((mappedName.equals("renderItemOverlayIntoGUI") || mappedName.equals("func_180453_a"))
                    && Type.getReturnType(desc) == Type.VOID_TYPE) {
                return new RenderItemOverlayIntoGuiTransformer(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class RenderItemOverlayIntoGuiTransformer extends MethodVisitor {

        private int state = 0;
        @Nullable
        private Label injectionSite = null;

        RenderItemOverlayIntoGuiTransformer(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
            if (state == 0) {
                String mappedName = FMLDeobfuscatingRemapper.INSTANCE.mapMethodName(owner, name, desc);
                if ((opcode == Opcodes.INVOKEVIRTUAL && mappedName.equals("showDurabilityBar"))
                        || (opcode == Opcodes.INVOKESTATIC && mappedName.equals("isItemDamaged"))) { // fuck optifine
                    state = 1;
                }
            }
            super.visitMethodInsn(opcode, owner, name, desc, itf);
        }

        @Override
        public void visitJumpInsn(int opcode, Label label) {
            if (state == 1) {
                injectionSite = label;
                state = 2;
            }
            super.visitJumpInsn(opcode, label);
        }

        @Override
        public void visitLabel(Label label) {
            super.visitLabel(label);
            if (state == 2 && label.equals(injectionSite)) {
                state = 3;
            }
        }

        @Override
        public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
            super.visitFrame(type, nLocal, local, nStack, stack);
            if (state == 3) {
                inject(); // just assume the stack frame is good
                state = 4;
            }
        }

        private void inject() {
            super.visitVarInsn(Opcodes.ALOAD, 2);
            super.visitVarInsn(Opcodes.ILOAD, 3);
            super.visitVarInsn(Opcodes.ILOAD, 4);
            super.visitMethodInsn(
                    Opcodes.INVOKESTATIC,
                    "xyz/phanta/tconevo/client/handler/ItemStackBarHandler",
                    "handleRender",
                    "(Lnet/minecraft/item/ItemStack;II)V",
                    false);
            System.out.println("[tconevo] Successfully injected item stack bar overlay handler.");
        }

    }

}
