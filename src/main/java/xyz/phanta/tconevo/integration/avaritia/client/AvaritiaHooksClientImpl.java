package xyz.phanta.tconevo.integration.avaritia.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;
import slimeknights.tconstruct.library.client.model.MaterialModel;
import slimeknights.tconstruct.library.client.model.ModifierModel;
import slimeknights.tconstruct.library.client.model.ToolModel;
import slimeknights.tconstruct.library.client.model.format.AmmoPosition;
import slimeknights.tconstruct.library.client.model.format.ToolModelOverride;
import xyz.phanta.tconevo.integration.avaritia.AvaritiaHooksImpl;

import java.util.List;

public class AvaritiaHooksClientImpl extends AvaritiaHooksImpl {

    @Override
    public MaterialModel createCustomMaterialModel(ImmutableList<ResourceLocation> textures, int offsetX, int offsetY, boolean renderHalo) {
        return new AvaritiaMaterialModel(textures, offsetX, offsetY, renderHalo);
    }

    @Override
    public ToolModel createCustomToolModel(ImmutableList<ResourceLocation> defaultTextures,
                                           List<MaterialModel> parts, List<MaterialModel> brokenParts,
                                           Float[] layerRotations, ModifierModel modifiers,
                                           ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
                                           ImmutableList<ToolModelOverride> overrides, AmmoPosition ammoPosition) {
        return new AvaritiaToolModel(defaultTextures, parts, brokenParts, layerRotations, modifiers, transforms, overrides, ammoPosition);
    }

}
