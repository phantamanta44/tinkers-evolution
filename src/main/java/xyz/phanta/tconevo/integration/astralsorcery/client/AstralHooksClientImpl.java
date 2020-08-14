package xyz.phanta.tconevo.integration.astralsorcery.client;

import hellfirepvp.astralsorcery.common.constellation.cape.CapeEffectFactory;
import hellfirepvp.astralsorcery.common.constellation.cape.CapeEffectRegistry;
import hellfirepvp.astralsorcery.common.constellation.cape.impl.CapeEffectMineralis;
import hellfirepvp.astralsorcery.common.item.wearable.ItemCape;
import hellfirepvp.astralsorcery.common.lib.Constellations;
import hellfirepvp.astralsorcery.common.lib.ItemsAS;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.nbt.NBTTagCompound;
import xyz.phanta.tconevo.integration.astralsorcery.AstralHooksImpl;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

@Reflected
public class AstralHooksClientImpl extends AstralHooksImpl {

    @SuppressWarnings("unchecked")
    @Override
    public void drawMineralisArmourEffect(EntityPlayer player) {
        // we don't want to draw the effect if the astral cape is already doing it
        if (player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem() == ItemsAS.armorImbuedCape
                && ItemCape.getCapeEffect(player, Constellations.mineralis) != null) {
            return;
        }
        // why is this so messy
        CapeEffectFactory<CapeEffectMineralis> factory = (CapeEffectFactory<CapeEffectMineralis>)CapeEffectRegistry
                .getArmorEffect(Constellations.mineralis);
        if (factory == null) {
            return;
        }
        factory.deserializeCapeEffect(new NBTTagCompound()).playClientHighlightTick(player);
    }

}
