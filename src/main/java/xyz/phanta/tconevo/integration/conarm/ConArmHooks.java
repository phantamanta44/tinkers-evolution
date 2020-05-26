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

    boolean isArmourModifierTrait(IModifier mod);

    @Nullable
    EntityEquipmentSlot getArmourType(NBTTagCompound rootTag);

    void rebuildArmour(NBTTagCompound rootTag, Item item) throws TinkerGuiException;

    class Noop implements ConArmHooks {

        @Override
        public boolean isArmourModifierTrait(IModifier mod) {
            return false;
        }

        @Nullable
        @Override
        public EntityEquipmentSlot getArmourType(NBTTagCompound rootTag) {
            return null;
        }

        @Override
        public void rebuildArmour(NBTTagCompound rootTag, Item item) throws TinkerGuiException {
            // NO-OP
        }

    }

}
