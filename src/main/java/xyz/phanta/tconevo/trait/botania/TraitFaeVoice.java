package xyz.phanta.tconevo.trait.botania;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.botania.BotaniaHooks;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.List;

public class TraitFaeVoice extends AbstractTrait {

    public static final int COLOUR = 0xb93c98;

    public TraitFaeVoice() {
        super(NameConst.TRAIT_FAE_VOICE, COLOUR);
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target,
                         float damageDealt, boolean wasCritical, boolean wasHit) {
        if (player.world.isRemote || !wasHit || !(player instanceof EntityPlayer)) {
            return;
        }
        double odds = TconEvoConfig.moduleBotania.faeVoiceProbabilityWeapon;
        if (odds > 0D && (odds >= 1D || random.nextDouble() <= odds)) {
            BotaniaHooks.INSTANCE.spawnPixie((EntityPlayer)player, target);
            target.hurtResistantTime = 0; // clear i-frames so the pixie can hit them
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        return ToolUtils.formatExtraInfoPercent(identifier, (float)TconEvoConfig.moduleBotania.faeVoiceProbabilityWeapon);
    }

}
