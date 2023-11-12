package xyz.phanta.tconevo.coremod.transform;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import xyz.phanta.tconevo.coremod.TconEvoClassTransformer;

import java.util.function.Consumer;

// gregtech has code that forces crashes in deobf environments if any bad recipes are registered
// gregicality has code that always registers a bad recipe
// this is a stupid workaround that allows these mods to load in a deobf environment
public class TransformGregTechRecipeCrash implements TconEvoClassTransformer.Transform {

    @Override
    public String getName() {
        return "GregTech Recipe Crash Prevention";
    }

    @Override
    public void getClasses(Consumer<String> collector) {
        collector.accept("gregtech.GregTechMod");
    }

    @Override
    public ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream) {
        return new ClassTransformerGregTechMod(apiVersion, downstream);
    }

    private static class ClassTransformerGregTechMod extends ClassVisitor {

        public ClassTransformerGregTechMod(int api, ClassVisitor cv) {
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
