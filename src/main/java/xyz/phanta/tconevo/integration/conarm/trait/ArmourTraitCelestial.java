package xyz.phanta.tconevo.integration.conarm.trait;

import c4.conarm.lib.modifiers.ArmorModifierTrait;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import xyz.phanta.tconevo.constant.NameConst;

public class ArmourTraitCelestial extends ArmorModifierTrait {

    public ArmourTraitCelestial() {
        super(NameConst.TRAIT_CELESTIAL, 0xedb9f4);
    }

    @Override
    public void onArmorTick(ItemStack tool, World world, EntityPlayer player) {
        if (world.isRemote || player.isSpectator()) {
            return;
        }
        if (!player.capabilities.allowFlying) {
            player.capabilities.allowFlying = true;
            player.sendPlayerAbilities();
        }
    }

    @Override
    public void onArmorRemoved(ItemStack armor, EntityPlayer player, int slot) {
        if (player.world.isRemote || player.capabilities.isCreativeMode || player.isSpectator()) {
            return;
        }
        // don't cancel flight if there is another armour piece with flight
        for (ItemStack stack : player.inventory.armorInventory) {
            if (isToolWithTrait(stack)) {
                return;
            }
        }
        player.capabilities.allowFlying = player.capabilities.isFlying = false;
        player.sendPlayerAbilities();
    }

}
