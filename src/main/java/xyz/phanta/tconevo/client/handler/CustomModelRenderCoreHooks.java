package xyz.phanta.tconevo.client.handler;

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
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.integration.avaritia.AvaritiaHooks;
import xyz.phanta.tconevo.util.Reflected;

import java.util.List;

// no reflection occurs here; these methods are called from code injected by coremods
// see TransformCustomMaterialRender
public class CustomModelRenderCoreHooks {

    @Reflected
    public static MaterialModel createMaterialModel(ImmutableList<ResourceLocation> textures, boolean renderHalo) {
        if (!TconEvoConfig.client.useFancyModelRenders) {
            return new MaterialModel(textures);
        }
        return createMaterialModel(textures, 0, 0, renderHalo);
    }

    @Reflected
    public static MaterialModel createMaterialModel(ImmutableList<ResourceLocation> textures, int offsetX, int offsetY,
                                                    boolean renderHalo) {
        if (TconEvoConfig.client.useFancyModelRenders) {
            MaterialModel customModel = AvaritiaHooks.INSTANCE.createCustomMaterialModel(textures, offsetX, offsetY, renderHalo);
            if (customModel != null) {
                return customModel;
            }
        }
        return new MaterialModel(textures, offsetX, offsetY);
    }

    @Reflected
    public static ToolModel createToolModel(ImmutableList<ResourceLocation> defaultTextures,
                                            List<MaterialModel> parts, List<MaterialModel> brokenParts,
                                            Float[] layerRotations, ModifierModel modifiers,
                                            ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
                                            ImmutableList<ToolModelOverride> overrides, AmmoPosition ammoPosition) {
        if (TconEvoConfig.client.useFancyModelRenders) {
            ToolModel customModel = AvaritiaHooks.INSTANCE.createCustomToolModel(
                    defaultTextures, parts, brokenParts, layerRotations, modifiers, transforms, overrides, ammoPosition);
            if (customModel != null) {
                return customModel;
            }
        }
        return new ToolModel(
                defaultTextures, parts, brokenParts, layerRotations, modifiers, transforms, overrides, ammoPosition);
    }

}
