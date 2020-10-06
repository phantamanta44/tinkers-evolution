package xyz.phanta.tconevo.integration.draconicevolution;

import com.brandon3055.draconicevolution.DEFeatures;
import com.brandon3055.draconicevolution.api.OreDictHelper;
import com.brandon3055.draconicevolution.api.fusioncrafting.ICraftingInjector;
import com.brandon3055.draconicevolution.api.fusioncrafting.IFusionCraftingInventory;
import com.brandon3055.draconicevolution.api.fusioncrafting.IFusionRecipe;
import com.brandon3055.draconicevolution.items.ToolUpgrade;
import io.github.phantamanta44.libnine.util.helper.OptUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tinkering.ITinkerable;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.util.ToolUtils;

import javax.annotation.Nullable;
import java.util.*;

public class DraconicUpgradeRecipe implements IFusionRecipe {

    private static final int[] UPGRADE_COSTS = { 32000, 512000, 32000000, 256000000 };

    private final Modifier upgradeMod;
    private final ItemStack upgradeKey;
    private final int tier;
    private final List<Object> ingredients;

    public DraconicUpgradeRecipe(Modifier upgradeMod, String upgradeKey, int tier, Object... ingredients) {
        if (tier < 0 || tier >= UPGRADE_COSTS.length) {
            throw new IllegalArgumentException("Bad draconic upgrade level: " + tier);
        }
        this.upgradeMod = upgradeMod;
        this.upgradeKey = new ItemStack(DEFeatures.toolUpgrade, 1, ToolUpgrade.NAME_TO_ID.get(upgradeKey));
        this.tier = tier;
        this.ingredients = new ArrayList<>(Arrays.asList(ingredients));
        this.ingredients.add(this.upgradeKey);
    }

    @Override
    public int getRecipeTier() {
        return tier;
    }

    @Override
    public List<Object> getRecipeIngredients() {
        return ingredients;
    }

    @Override
    public long getIngredientEnergyCost() {
        return UPGRADE_COSTS[tier];
    }

    @Override
    public ItemStack getRecipeCatalyst() {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean isRecipeCatalyst(ItemStack stack) {
        if (!(stack.getItem() instanceof ITinkerable)) {
            return false;
        }
        return OptUtils.stackTag(stack)
                .map(t -> (TinkerUtil.hasTrait(t, NameConst.TRAIT_EVOLVED) || TinkerUtil.hasTrait(t, NameConst.ARMOUR_TRAIT_EVOLVED))
                        && TinkerUtil.hasTrait(t, upgradeMod.identifier))
                .orElse(false);
    }

    @Override
    public ItemStack getRecipeOutput(@Nullable ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return ItemStack.EMPTY;
        }
        ItemStack result = stack.copy();
        try {
            upgradeMod.apply(result);
            // hopefully not a big problem that the player here is null...
            TinkerCraftingEvent.ToolModifyEvent.fireEvent(result, null, stack.copy());
            ToolUtils.rebuildToolStack(result);
        } catch (TinkerGuiException e) {
            return ItemStack.EMPTY;
        }
        return result;
    }

    @Override
    public boolean matches(IFusionCraftingInventory inv, World world, BlockPos pos) {
        if (!isRecipeCatalyst(inv.getStackInCore(0))) {
            return false;
        }
        Set<ICraftingInjector> injectors = new HashSet<>(inv.getInjectors());
        // check to make sure all ingredients have a match
        ing_iter:
        for (Object ing : ingredients) {
            Iterator<ICraftingInjector> iter = injectors.iterator();
            while (iter.hasNext()) {
                ItemStack injStack = iter.next().getStackInPedestal();
                if (!injStack.isEmpty() && OreDictHelper.areStacksEqual(ing, injStack)) {
                    iter.remove();
                    continue ing_iter;
                }
            }
            return false; // only falls through to here if no pedestals match the ingredient
        }
        // check to make sure remaining injectors are empty
        for (ICraftingInjector inj : injectors) {
            if (!inj.getStackInPedestal().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    // adapted from DE's FusionUpgradeRecipe#canCraft
    @Override
    public String canCraft(IFusionCraftingInventory inv, World world, BlockPos pos) {
        ItemStack tool = inv.getStackInCore(0);
        if (!isRecipeCatalyst(tool)) {
            return "upgrade.de.upgradeNA.info";
        } else if (!inv.getStackInCore(1).isEmpty()) {
            return "outputObstructed";
        }
        try {
            if (!upgradeMod.canApply(tool, tool)) {
                return "upgrade.de.upgradeApplied.info"; // assume this is the problem
            }
        } catch (TinkerGuiException e) {
            return e.getMessage();
        }
        int currentLevel = ToolUtils.getTraitLevel(tool, upgradeMod.identifier);
        if (currentLevel <= tier) {
            return "upgrade.de.upgradePrevLevelRequired.info";
        } else if (currentLevel > tier + 1) {
            return "upgrade.de.upgradeApplied.info";
        }
        for (ICraftingInjector inj : inv.getInjectors()) {
            if (!inj.getStackInPedestal().isEmpty() && inj.getPedestalTier() < tier) {
                return "tierLow";
            }
        }
        return "true";
    }

    @Override
    public void craft(IFusionCraftingInventory inv, World world, BlockPos pos) {
        if (matches(inv, world, pos)) {
            // remove ingredients
            Set<ICraftingInjector> injectors = new HashSet<>(inv.getInjectors());
            ing_iter:
            for (Object ing : ingredients) {
                Iterator<ICraftingInjector> iter = injectors.iterator();
                while (iter.hasNext()) {
                    ICraftingInjector inj = iter.next();
                    ItemStack injStack = inj.getStackInPedestal();
                    if (!injStack.isEmpty() && OreDictHelper.areStacksEqual(ing, injStack)) {
                        if (ing != upgradeKey) {
                            if (injStack.getItem().hasContainerItem(injStack)) {
                                inj.setStackInPedestal(injStack.getItem().getContainerItem(injStack));
                            } else if (injStack.getCount() == 1) {
                                inj.setStackInPedestal(ItemStack.EMPTY);
                            } else {
                                injStack.shrink(1);
                            }
                        }
                        iter.remove();
                        continue ing_iter;
                    }
                }
                // theoretically should not be able to fall through to here
            }
            // apply tool modifier
            ItemStack output = getRecipeOutput(inv.getStackInCore(0));
            inv.setStackInCore(0, ItemStack.EMPTY);
            inv.setStackInCore(1, output);
        }
    }

    @Override
    public void onCraftingTick(IFusionCraftingInventory inv, World world, BlockPos pos) {
        // NO-OP
    }

}
