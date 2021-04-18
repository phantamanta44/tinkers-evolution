package xyz.phanta.tconevo.handler;

import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import slimeknights.tconstruct.library.TinkerAPIException;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;

import javax.annotation.Nullable;

// not actually reflected; this is invoked by code injected in the coremod
// see TransformCaptureMaterialProperties and TransformCaptureDuplicateMaterials
public class MaterialPropertyCoreHooks {

    @Reflected
    public static void addTrait(Material material, ITrait trait, @Nullable String partType) {
        MaterialOverrideHandler.putOverriddenTrait(material, partType, trait);
    }

    @Reflected
    public static void handleDuplicateMaterial(String errorMsg, Object[] errorMsgArgs, Material material) {
        if (TconEvoConfig.overrideMaterials) {
            Material overridingMaterial = TinkerRegistry.getMaterial(material.identifier);
            if (TinkerRegistry.getTrace(overridingMaterial).matches(TconEvoMod.INSTANCE)) {
                ModContainer owningMod = Loader.instance().activeModContainer();
                TconEvoMod.LOGGER.info("Overriding duplicate material {} registered by {}",
                        material.identifier, owningMod != null ? owningMod.getModId() : "unknown");
                MaterialOverrideHandler.override(overridingMaterial.identifier, material);
                return;
            }
        }
        throw new TinkerAPIException(String.format(errorMsg, errorMsgArgs));
    }

}
