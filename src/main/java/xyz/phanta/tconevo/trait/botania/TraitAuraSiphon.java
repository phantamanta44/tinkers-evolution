package xyz.phanta.tconevo.trait.botania;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.botania.BotaniaHooks;
import xyz.phanta.tconevo.integration.botania.BotaniaIntItems;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class TraitAuraSiphon extends AbstractTrait {

    public TraitAuraSiphon() {
        super(NameConst.TRAIT_AURA_SIPHON, 0xb83007);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (player.world.isRemote || !wasHit || !(player instanceof EntityPlayer)) {
            return;
        }
        int mana = Math.round(damageDealt * (float)TconEvoConfig.moduleBotania.auraSiphonMultiplier);
        if (mana > 0) {
            BotaniaHooks.INSTANCE.dispatchMana(new ItemStack(BotaniaIntItems.MANA_GIVER), (EntityPlayer)player, mana, true);
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, (float)TconEvoConfig.moduleBotania.auraSiphonMultiplier);
    }

}
