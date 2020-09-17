package xyz.phanta.tconevo.handler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.integration.conarm.ConArmHooks;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

// methods aren't reflected, but are invoked from code injected by the coremod
public class TinkerToolPropCoreHooks {

    @Reflected
    public static void damageItem(ItemStack stack, int amount, EntityLivingBase wielder) {
        if (stack.getItem() instanceof ToolCore) {
            ToolHelper.damageTool(stack, amount, wielder);
        } else if (ConArmHooks.INSTANCE.isTinkerArmour(stack)) {
            ConArmHooks.INSTANCE.damageArmour(stack, amount, wielder);
        } else {
            stack.damageItem(amount, wielder);
        }
    }

    // more or less copied from ForgeHooks::isToolEffective, but uses a passed-in state instead of checking the world
    // extraneous args are there because it's easier to pass them than to drop them in the coremod
    @Reflected
    public static boolean isToolEffective(IBlockAccess world, BlockPos pos, ItemStack stack, IBlockState state) {
        for (String toolClass : stack.getItem().getToolClasses(stack)) {
            if (state.getBlock().isToolEffective(toolClass, state)) {
                return true;
            }
        }
        return false;
    }

}
