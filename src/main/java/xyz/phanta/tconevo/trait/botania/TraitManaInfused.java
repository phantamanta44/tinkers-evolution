package xyz.phanta.tconevo.trait.botania;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.botania.BotaniaHooks;

public class TraitManaInfused extends AbstractTrait {

    public static final int COLOUR = 0x3dcbde;

    public TraitManaInfused() {
        super(NameConst.TRAIT_MANA_INFUSED, COLOUR);
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        return (entity instanceof EntityPlayer && requestMana(tool, (EntityPlayer)entity)) ? 0 : newDamage;
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (entity instanceof EntityPlayer && tool.getItemDamage() > 0 && !ToolHelper.isBroken(tool)
                && requestMana(tool, (EntityPlayer)entity)) {
            ToolHelper.healTool(tool, 1, (EntityLivingBase)entity);
        }
    }

    public static boolean requestMana(ItemStack stack, EntityPlayer player) {
        return BotaniaHooks.INSTANCE.requestManaExactDiscounted(stack, player, TconEvoConfig.moduleBotania.manaInfusedCost, true);
    }

    @Override
    public int getPriority() {
        return 25;
    }

}
