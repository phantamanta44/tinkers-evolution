package xyz.phanta.tconevo.integration.conarm.trait.botania;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.trait.botania.TraitManaInfused;

public class ArmourTraitManaInfused extends AbstractArmorTrait {

    public ArmourTraitManaInfused() {
        super(NameConst.TRAIT_MANA_INFUSED, TraitManaInfused.COLOUR);
    }

    @Override
    public int onArmorDamage(ItemStack armour, DamageSource source, int damage, int newDamage, EntityPlayer player, int slot) {
        return TraitManaInfused.requestMana(armour, player) ? 0 : newDamage;
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {
        if (tool.getItemDamage() > 0 && !ToolHelper.isBroken(tool) && TraitManaInfused.requestMana(tool, player)) {
            ArmorHelper.healArmor(tool, 1, player, EntityLiving.getSlotForItemStack(tool).getIndex());
        }
    }

    @Override
    public int getPriority() {
        return 25;
    }

}
