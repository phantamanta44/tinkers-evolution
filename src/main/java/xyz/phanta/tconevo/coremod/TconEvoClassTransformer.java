package xyz.phanta.tconevo.coremod;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TconEvoClassTransformer implements IClassTransformer {

    private static final Map<String, Transform> TRANSFORMS = new HashMap<>();

    static {
        for (Transform tform : Arrays.asList(
                new TransformAstralAttunement(),
                new TransformCaptureMaterialProperties(),
                new TransformGregTechRecipeCrash(),
                new TransformItemSensitiveModifiers(),
                new TransformItemStackBar(),
                new TransformThaumInfusionEnchantment(),
                new TransformUniquePartTraits())) {
            tform.getClasses(c -> TRANSFORMS.put(c, tform));
        }
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] code) {
        Transform tform = TRANSFORMS.get(transformedName);
        if (tform != null) {
            TconEvoCoreMod.LOGGER.info("Applying transform \"{}\" to class: {}\n", tform.getName(), transformedName);
            ClassReader reader = new ClassReader(code);
            ClassWriter writer = new ClassWriter(reader, tform.getWriteFlags());
            reader.accept(tform.createTransformer(transformedName, Opcodes.ASM5, writer), tform.getReadFlags());
            return writer.toByteArray();
        }
        return code;
    }

    interface Transform {

        String getName();

        void getClasses(Consumer<String> collector);

        ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream);

        default int getReadFlags() {
            return 0;
        }

        default int getWriteFlags() {
            return 0;
        }

    }

}
