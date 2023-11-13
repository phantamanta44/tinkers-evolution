package xyz.phanta.tconevo.integration.conarm;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import xyz.phanta.tconevo.artifact.type.ArtifactType;
import xyz.phanta.tconevo.artifact.type.ArtifactTypeArmour;
import xyz.phanta.tconevo.integration.IntegrationHooks;

import javax.annotation.Nullable;

public interface ConArmHooks extends IntegrationHooks {

    String MOD_ID = "conarm";

    @Inject(value = MOD_ID, sided = true)
    ConArmHooks INSTANCE = new Noop();

    void registerModifiers();

    boolean isArmourModifierTrait(IModifier mod);

    @Nullable
    EntityEquipmentSlot getArmourType(NBTTagCompound rootTag);

    void rebuildArmour(NBTTagCompound rootTag, Item item) throws TinkerGuiException;

    boolean hasArmourModMatches(IModifier mod);

    boolean isTinkerArmour(ItemStack stack);

    void damageArmour(ItemStack stack, int amount, EntityLivingBase wearer);

    ItemStack buildArmourArtifact(ArtifactTypeArmour.Spec spec) throws ArtifactType.BuildingException;

    void addArmourXpFromDamage(ItemStack stack, float damage, EntityPlayer player);

    class Noop implements ConArmHooks {

        @Override
        public void registerModifiers() {
            // NO-OP
        }

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
        public void rebuildArmour(NBTTagCompound rootTag, Item item) {
            // NO-OP
        }

        @Override
        public boolean hasArmourModMatches(IModifier mod) {
            return false;
        }

        @Override
        public boolean isTinkerArmour(ItemStack stack) {
            return false;
        }

        @Override
        public void damageArmour(ItemStack stack, int amount, EntityLivingBase wearer) {
            // NO-OP
        }

        @Override
        public ItemStack buildArmourArtifact(ArtifactTypeArmour.Spec spec) throws ArtifactType.BuildingException {
            throw new ArtifactType.BuildingException("Construct's Armoury is not available");
        }

        @Override
        public void addArmourXpFromDamage(ItemStack stack, float damage, EntityPlayer player) {
            // NO-OP
        }

    }

}
