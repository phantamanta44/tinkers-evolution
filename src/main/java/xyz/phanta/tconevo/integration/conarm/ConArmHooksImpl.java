package xyz.phanta.tconevo.integration.conarm;

import c4.conarm.lib.events.ArmoryEvent;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import c4.conarm.lib.tinkering.ArmorBuilder;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.utils.RecipeMatchHolder;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.conarm.material.ArmourMaterialDefinition;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;

import javax.annotation.Nullable;

public class ConArmHooksImpl implements ConArmHooks {

    private static final String TAG_EQ_SLOT = "ConArmEquipmentSlot";
    private static final EntityEquipmentSlot[] EQ_SLOTS = EntityEquipmentSlot.values();

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        TconEvoArmourMaterials.init();
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        ArmourMaterialDefinition.initMaterialTraits();
        TconEvoArmourTraits.initModifierMaterials();
        // draconic evolution evolved upgrade recipes
        // have to hardcode upgrade key strings because we don't want to load DE classes here
        DraconicHooks.INSTANCE.addUpgradeRecipes(TconEvoArmourTraits.MOD_DRACONIC_ENERGY, "rfCap");
        DraconicHooks.INSTANCE.addUpgradeRecipes(TconEvoArmourTraits.MOD_DRACONIC_SHIELD_CAPACITY, "shieldCap");
        DraconicHooks.INSTANCE.addUpgradeRecipes(TconEvoArmourTraits.MOD_DRACONIC_SHIELD_RECOVERY, "shieldRec");
        DraconicHooks.INSTANCE.addUpgradeRecipes(TconEvoArmourTraits.MOD_DRACONIC_MOVE_SPEED, "moveSpeed");
        DraconicHooks.INSTANCE.addUpgradeRecipes(TconEvoArmourTraits.MOD_DRACONIC_JUMP_BOOST, "jumpBoost");
    }

    @Override
    public boolean isArmourModifierTrait(IModifier mod) {
        return mod instanceof ArmorModifierTrait;
    }

    @Nullable
    @Override
    public EntityEquipmentSlot getArmourType(NBTTagCompound rootTag) {
        if (!rootTag.hasKey(TAG_EQ_SLOT)) {
            return null;
        }
        int eqNdx = rootTag.getInteger(TAG_EQ_SLOT);
        return (eqNdx < 0 || eqNdx >= EQ_SLOTS.length) ? null : EQ_SLOTS[eqNdx];
    }

    @SubscribeEvent
    public void onArmourBuilt(ArmoryEvent.OnItemBuilding event) {
        event.tag.setInteger(TAG_EQ_SLOT, event.armor.armorType.ordinal());
        // draconic modifier init was deferred to here if the tool was just built
        // this is because it's impossible to know what the armour type is during trait init
        if (TinkerUtil.hasTrait(event.tag, NameConst.ARMOUR_TRAIT_EVOLVED)) {
            TconEvoArmourTraits.TRAIT_EVOLVED.initDraconicModifiers(event.tag, event.armor.armorType);
        }
    }

    @Override
    public void rebuildArmour(NBTTagCompound rootTag, Item item) throws TinkerGuiException {
        ArmorBuilder.rebuildArmor(rootTag, (TinkersArmor)item);
    }

    @Override
    public boolean hasArmourModMatches(IModifier mod) {
        return RecipeMatchHolder.getRecipes(mod).map(m -> !m.isEmpty()).orElse(false);
    }

}
