package xyz.phanta.tconevo.client.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.shared.client.BakedColoredItemModel;
import xyz.phanta.tconevo.util.Reflected;

// no reflection occurs here; these methods are called from code injected by coremods
// see TransformBuiltInModelFix
public class BuiltInModelFixCoreHooks {

    @Reflected
    public static IBakedModel getBakedModelForItem(ItemStack stack, World world, EntityLivingBase entity) {
        return new BakedColoredItemModel(
                stack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, world, entity));
    }

}
