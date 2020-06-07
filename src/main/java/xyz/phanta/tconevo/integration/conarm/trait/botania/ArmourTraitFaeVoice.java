package xyz.phanta.tconevo.integration.conarm.trait.botania;

import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.traits.AbstractArmorTrait;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.botania.BotaniaHooks;
import xyz.phanta.tconevo.trait.botania.TraitFaeVoice;
import xyz.phanta.tconevo.util.ToolUtils;

import java.util.Collections;
import java.util.List;

public class ArmourTraitFaeVoice extends AbstractArmorTrait {

    public ArmourTraitFaeVoice() {
        super(NameConst.TRAIT_FAE_VOICE, TraitFaeVoice.COLOUR);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static double getProbability(ItemStack stack) {
        return TconEvoConfig.moduleBotania.getFaeVoiceProbabilityArmour(EntityLiving.getSlotForItemStack(stack));
    }

    // since we're doing this orthogonal to botania's own handler, players could potentially get two pixies per hit
    // but whatever lol
    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        EntityLivingBase victim = event.getEntityLiving();
        Entity attacker = event.getSource().getTrueSource();
        if (victim.world.isRemote || !(victim instanceof EntityPlayer) || !(attacker instanceof EntityLivingBase)) {
            return;
        }
        double odds = 0D;
        for (ItemStack stack : victim.getArmorInventoryList()) {
            if (stack.getItem() instanceof ArmorCore && isToolWithTrait(stack)) {
                odds += getProbability(stack);
            }
        }
        if (odds > 0D && (odds >= 1D || random.nextDouble() <= odds)) {
            BotaniaHooks.INSTANCE.spawnPixie((EntityPlayer)victim, (EntityLivingBase)attacker);
        }
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        if (tool.getItem() instanceof ArmorCore) {
            return ToolUtils.formatExtraInfoPercent(identifier, (float)getProbability(tool));
        }
        return Collections.emptyList();
    }

}
