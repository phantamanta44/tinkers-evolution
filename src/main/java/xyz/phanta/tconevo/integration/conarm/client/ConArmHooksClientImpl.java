package xyz.phanta.tconevo.integration.conarm.client;

import c4.conarm.lib.book.ArmoryBook;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.MobEffects;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.mantle.client.book.repository.FileRepository;
import xyz.phanta.tconevo.client.book.BookTransformerAppendModifiers;
import xyz.phanta.tconevo.integration.conarm.ConArmHooksImpl;
import xyz.phanta.tconevo.integration.conarm.TconEvoArmourTraits;
import xyz.phanta.tconevo.integration.conarm.trait.draconicevolution.ArmourModDraconicMoveSpeed;

public class ConArmHooksClientImpl extends ConArmHooksImpl {

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        super.onPostInit(event);
        ArmoryBook.INSTANCE.addTransformer(new BookTransformerAppendModifiers(
                new FileRepository("conarm:book"), true, c -> c.acceptAll(TconEvoArmourTraits.MODIFIERS)));
    }

    // offset the fov increase caused by speed modifiers, since armour mods are semi-permanent and big fovs make the game hard to play
    // adapted from DE's WyvernArmor#getNewFOV
    @SubscribeEvent
    public void onFovUpdate(FOVUpdateEvent event) {
        AttributeModifier attrMod = event.getEntity().getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED)
                .getModifier(ArmourModDraconicMoveSpeed.ATTR_SPEED);
        if (attrMod != null) {
            boolean fly = event.getEntity().capabilities.isFlying;
            float speedModifier = (float)attrMod.getAmount()
                    / (event.getEntity().isSprinting() ? (fly ? 1.35F : 1.5F) : (fly ? 1.8181818F : 2.0F));
            float newFov = event.getFov() - speedModifier;
            newFov += speedModifier * 0.25F;
            if (newFov < 1F && event.getEntity().getActivePotionEffect(MobEffects.SLOWNESS) == null) {
                newFov = 1F;
            }
            event.setNewfov(newFov);
        }
    }

}
