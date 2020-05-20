package xyz.phanta.tconevo.trait.draconicevolution;

import com.google.common.collect.Sets;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.modifiers.ModifierNBT;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.tinkering.Category;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import xyz.phanta.tconevo.util.ToolUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ModifierDraconic extends ModifierTrait {

    public static final int DEFAULT_COLOUR = 0x00aaaa;

    static final List<ModifierDraconic> allMods = new ArrayList<>();

    @Nullable
    private final Set<Category> eligibleCategories;

    public ModifierDraconic(String name, int colour, int maxLevel, Category... eligibleCategories) {
        super(name, colour, maxLevel + 1, 0);
        this.eligibleCategories = eligibleCategories.length > 0 ? Sets.newHashSet(eligibleCategories) : null;
        // slightly faster than direct remove() because freeModifier will likely be near the end of the list
        aspects.remove(aspects.lastIndexOf(ModifierAspect.freeModifier));
        allMods.add(this);
    }

    public ModifierDraconic(String name, Category... eligibleCategories) {
        this(name, DEFAULT_COLOUR, 4, eligibleCategories);
    }

    // child classes should override applyDraconicEffect instead of this
    @Override
    public final void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        super.applyEffect(rootCompound, modifierTag);
        int level = ModifierNBT.readTag(modifierTag).level;
        if (level > 1) {
            applyDraconicEffect(rootCompound, level - 1);
        }
    }

    protected void applyDraconicEffect(NBTTagCompound rootTag, int tier) {
        // NO-OP
    }

    public boolean isEligible(NBTTagCompound root) {
        if (eligibleCategories == null) {
            return true;
        }
        for (Category cat : TagUtil.getCategories(root)) {
            if (eligibleCategories.contains(cat)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getLeveledTooltip(int level, @Nullable String suffix) {
        return String.format("%s " + TextFormatting.GOLD + "%s%s",
                getLocalizedName(), I18n.format("upgrade.level." + (level - 1)), suffix);
    }

    protected int getDraconicTier(ItemStack stack) {
        return ToolUtils.getTraitLevel(stack, identifier) - 1;
    }

}
