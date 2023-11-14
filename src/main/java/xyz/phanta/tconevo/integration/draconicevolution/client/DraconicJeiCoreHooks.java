package xyz.phanta.tconevo.integration.draconicevolution.client;

import com.brandon3055.draconicevolution.api.fusioncrafting.IFusionRecipe;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import mezz.jei.api.gui.IRecipeLayout;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tools.ToolCore;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.integration.conarm.ConArmHooks;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicUpgradeRecipe;
import xyz.phanta.tconevo.util.TconReflect;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DraconicJeiCoreHooks {

    @Nullable
    private static List<ItemStack> cachedToolStacks, cachedArmourStacks;

    private static List<ItemStack> getToolStacks() {
        if (cachedToolStacks != null) {
            return cachedToolStacks;
        }
        cachedToolStacks = new ArrayList<>();
        for (ToolCore item : TinkerRegistry.getTools()) {
            NBTTagCompound tag = new NBTTagCompound();
            try {
                ToolBuilder.rebuildTool(tag, item);
            } catch (TinkerGuiException e) {
                // NO-OP
            }
            TagUtil.setCategories(tag, TconReflect.getCategories(item));
            TconEvoTraits.TRAIT_EVOLVED.apply(tag);
            ItemStack stack = new ItemStack(item);
            stack.setTagCompound(tag);
            cachedToolStacks.add(stack);
        }
        return cachedToolStacks;
    }

    private static List<ItemStack> getArmourStacks() {
        if (cachedArmourStacks != null) {
            return cachedArmourStacks;
        }
        cachedArmourStacks = new ArrayList<>();
        for (Item item : ConArmHooks.INSTANCE.getArmourItems()) {
            NBTTagCompound tag = new NBTTagCompound();
            try {
                ConArmHooks.INSTANCE.rebuildArmour(tag, item);
            } catch (TinkerGuiException e) {
                // NO-OP
            }
            ItemStack stack = new ItemStack(item);
            ITrait trait = TinkerRegistry.getTrait(NameConst.ARMOUR_TRAIT_EVOLVED);
            if (trait instanceof IModifier) {
                ((IModifier) trait).apply(tag);
            }
            stack.setTagCompound(tag);
            cachedArmourStacks.add(stack);
        }
        return cachedArmourStacks;
    }

    @Reflected
    public static void fixFusionRecipe(IRecipeLayout layout, IFusionRecipe recipe) {
        if (!(recipe instanceof DraconicUpgradeRecipe)) {
            return;
        }

        DraconicUpgradeRecipe upgradeRecipe = (DraconicUpgradeRecipe) recipe;
        Modifier mod = upgradeRecipe.getModifier();
        List<ItemStack> inputStacks = new ArrayList<>(), outputStacks = new ArrayList<>();
        for (ItemStack templateStack : (ConArmHooks.INSTANCE.isArmourModifierTrait(mod)
                ? getArmourStacks() : getToolStacks())) {
            NBTTagCompound templateTag = TagUtil.getTagSafe(templateStack);
            if (!TinkerUtil.hasModifier(templateTag, mod.identifier)) {
                continue;
            }
            NBTTagCompound tag = templateTag.copy();
            NBTTagCompound modTag = TinkerUtil.getModifierTag(tag, mod.identifier);
            ModifierNBT modData = ModifierNBT.readTag(modTag);

            modData.level = upgradeRecipe.getRecipeTier() + 1;
            modData.write(modTag);
            ItemStack inputStack = new ItemStack(templateStack.getItem());
            inputStack.setTagCompound(tag.copy());
            inputStacks.add(inputStack);

            ++modData.level;
            modData.write(modTag);
            ItemStack outputStack = new ItemStack(templateStack.getItem());
            outputStack.setTagCompound(tag);
            outputStacks.add(outputStack);
        }
        layout.getItemStacks().set(0, inputStacks);
        layout.getItemStacks().set(1, outputStacks);
    }

}
