package xyz.phanta.tconevo.trait;

import gnu.trove.map.TIntIntMap;
import gnu.trove.map.hash.TIntIntHashMap;
import io.github.phantamanta44.libnine.util.format.FormatUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.capability.PowerWrapper;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.ic2.Ic2Hooks;
import xyz.phanta.tconevo.trait.base.MatchSensitiveModifier;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModifierPhotovoltaic extends ModifierTrait implements MatchSensitiveModifier {

    public static final int COLOUR = 0x3b6ea7;

    private static final int DEFAULT_GEN_RATE = 1; // used if an item is matched with no registered gen rate
    private static final String TAG_GEN_RATE = "PhotovoltaicRate";

    private static final Map<Item, TIntIntMap> matches = new HashMap<>();

    public static void registerSolarItem(ItemStack stack, int genRate) {
        TconEvoMod.LOGGER.debug("Registering solar item \"{}\" generating {} RF/s", stack, genRate);
        matches.computeIfAbsent(stack.getItem(), k -> new TIntIntHashMap()).put(stack.getMetadata(), genRate);
    }

    public static int getModifierItemGenRate(ItemStack stack) {
        TIntIntMap itemMap = matches.get(stack.getItem());
        if (itemMap != null) {
            int genRate = itemMap.get(stack.getMetadata());
            if (genRate > 0) { // default no-entry value is 0
                return genRate;
            }
        }
        return 0;
    }

    public ModifierPhotovoltaic() {
        super(NameConst.MOD_PHOTOVOLTAIC, COLOUR);
        aspects.remove(aspects.lastIndexOf(ModifierAspect.freeModifier));
        addAspects(new ModifierAspect.FreeFirstModifierAspect(this, 1));
    }

    @Override
    public boolean canApplyCustom(ItemStack stack) {
        return PowerWrapper.isPowered(stack);
    }

    @Override
    public void applyEffectWithMatch(ItemStack tool, RecipeMatch.Match match) {
        NBTTagCompound tag = TagUtil.getTagSafe(tool);
        write_solar_data:
        {
            if (!match.stacks.isEmpty()) {
                int genRate = getModifierItemGenRate(match.stacks.get(0));
                if (genRate > 0) {
                    tag.setInteger(TAG_GEN_RATE, genRate);
                    break write_solar_data;
                }
            }
            tag.setInteger(TAG_GEN_RATE, DEFAULT_GEN_RATE);
        }
        tool.setTagCompound(tag);
    }

    public static int getToolGenRateMax(ItemStack tool) {
        return TagUtil.getTagSafe(tool).getInteger(TAG_GEN_RATE);
    }

    public static int getToolGenRate(ItemStack tool, World world, BlockPos pos) {
        int maxGenRate = getToolGenRateMax(tool);
        return maxGenRate > 0 ? Math.round(maxGenRate * Ic2Hooks.INSTANCE.getSunlight(world, pos)) : 0;
    }

    public static void handleGeneration(ItemStack tool, World world, Entity entity) {
        PowerWrapper energy = PowerWrapper.wrap(tool);
        if (energy != null) {
            if (energy.getEnergy() < energy.getEnergyMax()) {
                int genRate = getToolGenRate(tool, world, entity.getPosition());
                if (genRate > 0) {
                    energy.inject(genRate, true, true);
                }
            }
        }
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!world.isRemote && !isSelected && entity instanceof EntityLivingBase && entity.ticksExisted % 20 == 0) {
            handleGeneration(tool, world, entity);
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        int genRate = getToolGenRateMax(tool);
        return genRate > 0
                ? ToolUtils.formatExtraInfo(identifier, FormatUtils.formatSI(genRate, "RF/s"))
                : Collections.emptyList();
    }

}
