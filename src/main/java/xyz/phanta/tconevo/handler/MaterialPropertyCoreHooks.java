package xyz.phanta.tconevo.handler;

import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.traits.ITrait;
import xyz.phanta.tconevo.util.Reflected;

import javax.annotation.Nullable;

// not actually reflected; this is invoked by code injected in the coremod
// sett TransformCaptureMaterialProperties
public class MaterialPropertyCoreHooks {

    @Reflected
    public static void addTrait(Material material, ITrait trait, @Nullable String partType) {
        MaterialOverrideHandler.putOverriddenTrait(material, partType, trait);
    }

}
