package xyz.phanta.tconevo.trait.bloodmagic;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.bloodmagic.BloodMagicHooks;

public class TraitBloodbound extends AbstractTrait {

    public static final int COLOUR = 0x6b0205;

    public TraitBloodbound() {
        super(NameConst.TRAIT_BLOODBOUND, COLOUR);
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        return entity instanceof EntityPlayer
                ? doDamageReduction((EntityPlayer)entity, tool, newDamage, TconEvoConfig.moduleBloodMagic.bloodboundToolCost)
                : newDamage;
    }

    public static int doDamageReduction(EntityPlayer player, ItemStack tool, int damage, int unitCost) {
        if (player.world.isRemote) {
            return 0; // soul networks aren't handled at all on the client
        }
        int bloodCost = damage * unitCost;
        if (bloodCost > 0) {
            int consumed = BloodMagicHooks.INSTANCE.extractLifePoints(player, bloodCost, tool);
            if (consumed > 0) {
                return Math.max((int)Math.ceil(damage * (1F - consumed / (float)bloodCost)), 0);
            }
        }
        return damage;
    }

    @Override
    public int getPriority() {
        return 50;
    }

}
