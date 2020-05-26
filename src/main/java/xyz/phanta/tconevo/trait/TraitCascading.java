package xyz.phanta.tconevo.trait;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.constant.NameConst;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TraitCascading extends AbstractTrait {

    private final Set<UUID> processingCascading = new HashSet<>();

    public TraitCascading() {
        super(NameConst.TRAIT_CASCADING, 0xf379bc);
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase entity, boolean wasEffective) {
        if (wasEffective && !world.isRemote && entity instanceof EntityPlayer && state.getBlock() instanceof BlockFalling) {
            EntityPlayer player = (EntityPlayer)entity;
            // avoid recursing for the extra block breaks
            if (processingCascading.contains(player.getUniqueID())) {
                return;
            }
            processingCascading.add(player.getUniqueID());
            try {
                // alternate between advancing upwards and downwards
                // sort of like breadth-first search
                boolean moreUp = true, moreDown = true;
                BlockPos posUp = pos.up(), posDown = pos.down();
                while (moreUp || moreDown) {
                    if (moreUp) {
                        if (posUp.getY() <= world.getHeight() && world.getBlockState(posUp) == state) {
                            ToolHelper.breakExtraBlock(tool, world, player, posUp, pos);
                            posUp = posUp.up();
                        } else {
                            moreUp = false;
                        }
                    }
                    if (moreDown) {
                        if (posDown.getY() >= 0 && world.getBlockState(posDown) == state) {
                            ToolHelper.breakExtraBlock(tool, world, player, posDown, pos);
                            posDown = posDown.down();
                        } else {
                            moreDown = false;
                        }
                    }
                }
            } finally {
                processingCascading.remove(player.getUniqueID());
            }
        }
    }

}
