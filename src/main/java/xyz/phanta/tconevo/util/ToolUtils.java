package xyz.phanta.tconevo.util;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import slimeknights.tconstruct.library.Util;
import slimeknights.tconstruct.library.capability.projectile.CapabilityTinkerProjectile;
import slimeknights.tconstruct.library.capability.projectile.ITinkerProjectile;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tinkering.TinkersItem;
import slimeknights.tconstruct.library.tools.ProjectileLauncherNBT;
import slimeknights.tconstruct.library.tools.ProjectileNBT;
import slimeknights.tconstruct.library.tools.ToolNBT;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.Tags;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolBuilder;
import xyz.phanta.tconevo.integration.conarm.ConArmHooks;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ToolUtils {

    public static ItemStack getAttackerWeapon(DamageSource dmgSrc, EntityLivingBase attacker) {
        return CapabilityTinkerProjectile.getTinkerProjectile(dmgSrc)
                .map(ITinkerProjectile::getItemStack)
                .orElse(attacker.getHeldItem(EnumHand.MAIN_HAND));
    }

    public static NBTTagCompound getOrCreateTag(ItemStack stack) {
        if (stack.hasTagCompound()) {
            return Objects.requireNonNull(stack.getTagCompound());
        }
        NBTTagCompound tag = new NBTTagCompound();
        stack.setTagCompound(tag);
        return tag;
    }

    public static ProjectileNBT getProjectileData(NBTTagCompound tag) {
        return new ProjectileNBT(TagUtil.getToolTag(tag));
    }

    public static ProjectileNBT getOriginalProjectileData(NBTTagCompound tag) {
        return new ProjectileNBT(getOriginalToolTag(tag));
    }

    public static ProjectileLauncherNBT getLauncherData(NBTTagCompound tag) {
        return new ProjectileLauncherNBT(TagUtil.getToolTag(tag));
    }

    public static ProjectileLauncherNBT getOriginalLauncherData(NBTTagCompound tag) {
        return new ProjectileLauncherNBT(getOriginalToolTag(tag));
    }

    public static NBTTagCompound getOriginalToolTag(NBTTagCompound tag) {
        return TagUtil.getTagSafe(tag, Tags.TOOL_DATA_ORIG);
    }

    public static boolean hasTrait(ItemStack stack, String traitId) {
        return TinkerUtil.hasTrait(TagUtil.getTagSafe(stack), traitId);
    }

    public static int getTraitLevel(ItemStack stack, String traitId) {
        return ModifierNBT.readTag(TinkerUtil.getModifierTag(stack, traitId)).level;
    }

    public static List<String> formatExtraInfo(String identifier, String info) {
        return Collections.singletonList(I18n.format(String.format(Modifier.LOC_Extra, identifier), info));
    }

    public static List<String> formatExtraInfoPercent(String identifier, float percentage) {
        return formatExtraInfo(identifier, Util.dfPercent.format(percentage));
    }

    public static int getAndSetModifierCount(ItemStack stack, int mods) {
        ToolNBT toolData = TagUtil.getToolStats(stack);
        int oldModCount = toolData.modifiers;
        toolData.modifiers = mods;
        TagUtil.setToolTag(stack, toolData.get());
        return oldModCount;
    }

    public static void rebuildToolStack(ItemStack stack) throws TinkerGuiException {
        NBTTagCompound rootTag = TagUtil.getTagSafe(stack);
        if (stack.getItem() instanceof TinkersItem) {
            ToolBuilder.rebuildTool(rootTag, (TinkersItem)stack.getItem());
        } else { // if it's not a tool, it's probably armour
            ConArmHooks.INSTANCE.rebuildArmour(rootTag, stack.getItem());
        }
        stack.setTagCompound(rootTag);
    }

}
