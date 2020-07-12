package xyz.phanta.tconevo.integration.conarm.client;

import c4.conarm.lib.book.ArmoryBook;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.client.book.repository.FileRepository;
import xyz.phanta.tconevo.client.book.BookTransformerAppendModifiers;
import xyz.phanta.tconevo.client.book.BookTransformerListingOverflow;
import xyz.phanta.tconevo.integration.conarm.ConArmHooksImpl;
import xyz.phanta.tconevo.integration.conarm.TconEvoArmourTraits;
import xyz.phanta.tconevo.integration.conarm.trait.bloodmagic.ArmourTraitSentient;
import xyz.phanta.tconevo.integration.conarm.trait.draconicevolution.ArmourModDraconicMoveSpeed;
import xyz.phanta.tconevo.util.Reflected;

import java.util.UUID;

@Reflected
public class ConArmHooksClientImpl extends ConArmHooksImpl {

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        super.onPostInit(event);
        ArmoryBook.INSTANCE.addTransformer(new BookTransformerAppendModifiers(
                new FileRepository("conarm:book"), true, c -> c.acceptAll(TconEvoArmourTraits.MODIFIERS)));
        ArmoryBook.INSTANCE.addTransformer(new BookTransformerListingOverflow("modifiers"));
    }

    // offset the fov increase caused by speed modifiers, since armour mods are semi-permanent and big fovs make the game hard to play
    // adapted from DE's WyvernArmor#getNewFOV
    @SubscribeEvent
    public void onFovUpdate(FOVUpdateEvent event) {
        IAttributeInstance attr = event.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
        double speedModifier = getModifierAmount(attr, ArmourModDraconicMoveSpeed.ATTR_SPEED)
                + ArmourTraitSentient.ATTR_MOVE_SPD.streamIds().mapToDouble(id -> getModifierAmount(attr, id)).sum();
        if (speedModifier > 0D) {
            boolean fly = event.getEntity().capabilities.isFlying;
            float totalSpeedMod = (float)speedModifier / (event.getEntity().isSprinting()
                    ? (fly ? 1.35F : 1.5F) : (fly ? 1.8181818F : 2.0F));
            float newFov = event.getFov() - totalSpeedMod;
            newFov += totalSpeedMod * 0.25F;
            if (newFov < 1F && event.getEntity().getActivePotionEffect(MobEffects.SLOWNESS) == null) {
                newFov = 1F;
            }
            event.setNewfov(newFov);
        }
    }

    private static double getModifierAmount(IAttributeInstance attr, UUID id) {
        AttributeModifier attrMod = attr.getModifier(id);
        return attrMod != null ? attrMod.getAmount() : 0D;
    }

}
