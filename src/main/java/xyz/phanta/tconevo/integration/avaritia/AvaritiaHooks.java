package xyz.phanta.tconevo.integration.avaritia;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import slimeknights.tconstruct.library.client.model.MaterialModel;
import slimeknights.tconstruct.library.client.model.ModifierModel;
import slimeknights.tconstruct.library.client.model.ToolModel;
import slimeknights.tconstruct.library.client.model.format.AmmoPosition;
import slimeknights.tconstruct.library.client.model.format.ToolModelOverride;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public interface AvaritiaHooks extends IntegrationHooks {

    String MOD_ID = "avaritia";

    @Inject(value = MOD_ID, sided = true)
    AvaritiaHooks INSTANCE = new Noop();

    Optional<ItemStack> getItemNeutronPile();

    String formatRainbowText(String text);

    @SideOnly(Side.CLIENT)
    @Nullable
    default MaterialModel createCustomMaterialModel(ImmutableList<ResourceLocation> textures, int offsetX, int offsetY, boolean renderHalo) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    default ToolModel createCustomToolModel(ImmutableList<ResourceLocation> defaultTextures,
                                            List<MaterialModel> parts, List<MaterialModel> brokenParts,
                                            Float[] layerRotations, ModifierModel modifiers,
                                            ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
                                            ImmutableList<ToolModelOverride> overrides, AmmoPosition ammoPosition) {
        return null;
    }

    class Noop implements AvaritiaHooks {

        @Override
        public Optional<ItemStack> getItemNeutronPile() {
            return Optional.empty();
        }

        @Override
        public String formatRainbowText(String text) {
            return TextFormatting.AQUA + text;
        }

    }

}
