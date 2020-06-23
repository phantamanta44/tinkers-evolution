package xyz.phanta.tconevo.coremod;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.*;

// gregtech has code that forces crashes in deobf environments if any bad recipes are registered
// gregicality has code that always registers a bad recipe
// this is a stupid workaround that allows these mods to load in a deobf environment
public class ClassTransformerGregTechRecipeCrash implements IClassTransformer {

    @Override
    public byte[] transform(String name, String transformedName, byte[] code) {
        if (transformedName.equals("gregtech.GregTechMod")) {
            ClassReader reader = new ClassReader(code);
            ClassWriter writer = new ClassWriter(reader, 0);
            reader.accept(new ClassTransformerGregTechMod(Opcodes.ASM5, writer), 0);
            return writer.toByteArray();
        }
        return code;
    }

    private static class ClassTransformerGregTechMod extends ClassVisitor {

        public ClassTransformerGregTechMod(int api, ClassWriter cv) {
            super(api, cv);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            if (name.equals("onInit")) {
                return new MethodTransformerOnInit(api, super.visitMethod(access, name, desc, signature, exceptions));
            }
            return super.visitMethod(access, name, desc, signature, exceptions);
        }

    }

    private static class MethodTransformerOnInit extends MethodVisitor {

        public MethodTransformerOnInit(int api, MethodVisitor mv) {
            super(api, mv);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            if (opcode == Opcodes.GETSTATIC
                    && owner.equals("net/minecraftforge/classloading/FMLForgePlugin") && name.equals("RUNTIME_DEOBF")) {
                super.visitInsn(Opcodes.ICONST_1); // yield true, since it's negated by the next insn
            } else {
                super.visitFieldInsn(opcode, owner, name, desc);
            }
        }

    }

}
