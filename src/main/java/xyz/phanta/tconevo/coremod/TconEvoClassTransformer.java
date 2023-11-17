package xyz.phanta.tconevo.coremod;

import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import xyz.phanta.tconevo.coremod.transform.*;
import xyz.phanta.tconevo.coremod.util.SafeClassWriter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class TconEvoClassTransformer implements IClassTransformer {

    private static final boolean DEBUG = false;
    private static final Map<String, Transform> TRANSFORMS = new HashMap<>();

    static {
        for (Transform tform : Arrays.asList(
                new TransformAstralAttunement(),
                new TransformBreakUnbreakable(),
                new TransformCaptureDuplicateMaterials(),
                new TransformCaptureMaterialProperties(),
                new TransformCustomMaterialRender(),
                new TransformDisableDamageCutoff(),
                new TransformFixArmourDamage(),
                new TransformFixDraconicJei(),
                new TransformGregTechRecipeCrash(),
                new TransformImprovedToolBuilding(),
                new TransformItemStackBar(),
                new TransformMaterialisConArmCrash(),
                new TransformModifyMeltSpeed(),
                new TransformThaumInfusionEnchantment(),
                new TransformThaumVisDiscount(),
                new TransformUniquePartTraits(),
                new TransformUseJeiFancyRender())) {
            tform.getClasses(c -> TRANSFORMS.put(c, tform));
        }
    }

    @Override
    public byte[] transform(String name, String niceName, byte[] code) {
        Transform tform = TRANSFORMS.get(niceName);
        if (tform != null) {
            TconEvoCoreMod.LOGGER.info("Applying transform \"{}\" to class: {}", tform.getName(), niceName);
            ClassReader reader = new ClassReader(code);
            ClassWriter writer = new SafeClassWriter(reader, tform.getWriteFlags(), getClass().getClassLoader());
            reader.accept(tform.createTransformer(niceName, Opcodes.ASM5, writer), tform.getReadFlags());
            byte[] newCode = writer.toByteArray();
            if (DEBUG) {
                try {
                    Path outputDir = Paths.get("tconevo-coremod-debug");
                    Files.createDirectories(outputDir);
                    Files.write(outputDir.resolve(niceName.substring(niceName.lastIndexOf('.') + 1).replace('$', '_') + ".class"), newCode);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return newCode;
        }
        return code;
    }

    public interface Transform {

        String getName();

        default int getReadFlags() {
            return 0;
        }

        default int getWriteFlags() {
            return 0;
        }

        void getClasses(Consumer<String> collector);

        ClassVisitor createTransformer(String className, int apiVersion, ClassVisitor downstream);

    }

}
