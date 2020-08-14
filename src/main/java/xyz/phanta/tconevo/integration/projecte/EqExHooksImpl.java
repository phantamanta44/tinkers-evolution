package xyz.phanta.tconevo.integration.projecte;

import moze_intel.projecte.api.item.IItemEmc;
import moze_intel.projecte.api.state.PEStateProps;
import moze_intel.projecte.gameObjs.ObjHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

@Reflected
public class EqExHooksImpl implements EqExHooks {

    @Override
    public void onInit(FMLInitializationEvent event) {
        OreDictionary.registerOre("itemDarkMatter", new ItemStack(ObjHandler.matter, 1, 0));
        OreDictionary.registerOre("itemRedMatter", new ItemStack(ObjHandler.matter, 1, 1));
        OreDictionary.registerOre("blockDarkMatter", new ItemStack(ObjHandler.matterBlock, 1, 0));
        OreDictionary.registerOre("blockRedMatter", new ItemStack(ObjHandler.matterBlock, 1, 1));
    }

    @Override
    public long getEmcCapacity(ItemStack stack) {
        if (stack.getItem() instanceof IItemEmc) {
            return ((IItemEmc)stack.getItem()).getMaximumEmc(stack);
        }
        return 0L;
    }

    @Override
    public long getEmcStored(ItemStack stack) {
        if (stack.getItem() instanceof IItemEmc) {
            return ((IItemEmc)stack.getItem()).getStoredEmc(stack);
        }
        return 0L;
    }

    @Override
    public long injectEmc(ItemStack stack, long amount) {
        if (stack.getItem() instanceof IItemEmc) {
            return ((IItemEmc)stack.getItem()).addEmc(stack, amount);
        }
        return 0L;
    }

    @Override
    public int getDenseBlockTier(IBlockState state) {
        Block block = state.getBlock();
        if (block == ObjHandler.matterBlock) {
            switch (state.getValue(PEStateProps.TIER_PROP)) {
                case DARK_MATTER:
                    return 1;
                case RED_MATTER:
                    return 2;
            }
        }
        if (block == ObjHandler.dmFurnaceOff || block == ObjHandler.dmFurnaceOn) {
            return 1;
        } else if (block == ObjHandler.rmFurnaceOff || block == ObjHandler.rmFurnaceOn) {
            return 2;
        }
        return 0;
    }

}
