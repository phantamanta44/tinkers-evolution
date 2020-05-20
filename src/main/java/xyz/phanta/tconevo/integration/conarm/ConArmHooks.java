package xyz.phanta.tconevo.integration.conarm;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import javax.annotation.Nullable;

public interface ConArmHooks extends IntegrationHooks {

    String MOD_ID = "conarm";

    @IntegrationHooks.Inject(value = MOD_ID, sided = true)
    ConArmHooks INSTANCE = new Noop();

    default boolean isArmourModifierTrait(IModifier mod) {
        return false;
    }

    @Nullable
    default EntityEquipmentSlot getArmourType(NBTTagCompound rootTag) {
        return null;
    }

    default void rebuildArmour(NBTTagCompound rootTag, Item item) throws TinkerGuiException {
        // NO-OP
    }

    class Noop implements ConArmHooks {
        // NO-OP
    }

}
