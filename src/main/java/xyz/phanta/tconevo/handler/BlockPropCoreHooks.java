package xyz.phanta.tconevo.handler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import slimeknights.tconstruct.library.tools.ToolCore;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import xyz.phanta.tconevo.util.ToolUtils;

// no reflection occurs here; these methods are called from code injected by coremods
// see TransformBreakUnbreakable
public class BlockPropCoreHooks {

    @Reflected
    public static float getBlockStrength(float baseStrength, IBlockState state, EntityPlayer player, World world, BlockPos pos) {
        if (baseStrength < 0F && TconEvoConfig.moduleAvaritia.omnipotenceBreaksUnbreakable) {
            ItemStack stack = player.getHeldItemMainhand();
            if (stack.getItem() instanceof ToolCore && ToolUtils.hasTrait(stack, NameConst.TRAIT_OMNIPOTENCE)) {
                return ForgeHooks.canHarvestBlock(state.getBlock(), player, world, pos) ? 64F : 2.4F;
            }
        }
        return baseStrength;
    }

}
