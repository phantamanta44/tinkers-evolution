package xyz.phanta.tconevo.integration.conarm;

import c4.conarm.common.armor.utils.ArmorHelper;
import c4.conarm.lib.ArmoryRegistry;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.events.ArmoryEvent;
import c4.conarm.lib.modifiers.ArmorModifierTrait;
import c4.conarm.lib.tinkering.ArmorBuilder;
import c4.conarm.lib.tinkering.TinkersArmor;
import c4.conarm.lib.utils.RecipeMatchHolder;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.modifiers.TinkerGuiException;
import slimeknights.tconstruct.library.tinkering.PartMaterialType;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.tools.TinkerModifiers;
import xyz.phanta.tconevo.artifact.type.ArtifactType;
import xyz.phanta.tconevo.artifact.type.ArtifactTypeArmour;
import xyz.phanta.tconevo.artifact.type.ArtifactTypeTool;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.init.TconEvoTraits;
import xyz.phanta.tconevo.integration.conarm.material.ArmourMaterialDefinition;
import xyz.phanta.tconevo.integration.draconicevolution.DraconicHooks;
import xyz.phanta.tconevo.integration.gamestages.GameStagesHooks;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

@Reflected
public class ConArmHooksImpl implements ConArmHooks {

    private static final String TAG_EQ_SLOT = "ConArmEquipmentSlot";
    private static final EntityEquipmentSlot[] EQ_SLOTS = EntityEquipmentSlot.values();

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
        TconEvoArmourMaterials.init();
    }

    @Override
    public void registerModifiers() {
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
        if (TinkerUtil.hasTrait(event.tag, NameConst.ARMOUR_TRAIT_EVOLVED)) {
            // draconic modifier init was deferred to here if the tool was just built
            // this is because it's impossible to know what the armour type is during trait init
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

    @Override
    public boolean isTinkerArmour(ItemStack stack) {
        return stack.getItem() instanceof TinkersArmor;
    }

    @Override
    public void damageArmour(ItemStack stack, int amount, EntityLivingBase wearer) {
        if (wearer instanceof EntityPlayer) { // this probably won't cause any huge issues
            ArmorHelper.damageArmor(stack, DamageSource.GENERIC, amount, (EntityPlayer)wearer);
        }
    }

    @Override
    public ItemStack buildArmourArtifact(ArtifactTypeArmour.Spec spec) throws ArtifactType.BuildingException {
        // get armour type
        // this is bad and slow, but conarm doesn't offer any better way to look up armour types
        ArmorCore armourType = ArmoryRegistry.getArmor().stream()
                .filter(t -> t.getIdentifier().equals(spec.armourType))
                .findAny().orElseThrow(() -> new ArtifactType.BuildingException("Unknown armour type \"%s\"", spec.armourType));

        // get material types
        List<PartMaterialType> componentTypes = armourType.getRequiredComponents();
        if (spec.materials.size() != componentTypes.size()) {
            throw new ArtifactType.BuildingException("Needed %d materials but got %d for armour type \"%s\"",
                    componentTypes.size(), spec.materials.size(), armourType.getIdentifier());
        }
        List<Material> materials = ArtifactTypeTool.resolveMaterials(spec.materials);

        // build component stacks
        NonNullList<ItemStack> components = NonNullList.create();
        for (int i = 0; i < materials.size(); i++) {
            Set<IToolPart> parts = componentTypes.get(i).getPossibleParts();
            if (parts.isEmpty()) {
                throw new ArtifactType.BuildingException("Unsatisfiable part %d for armour type \"%s\"", i, armourType.getIdentifier());
            }
            components.add(parts.iterator().next().getItemstackWithMaterial(materials.get(i)));
        }

        // build armour piece
        ItemStack stack = armourType.buildItem(materials);
        stack.setStackDisplayName(ArtifactType.ARTIFACT_FMT + spec.name);
        try {
            GameStagesHooks.INSTANCE.startBypass();
            TinkerCraftingEvent.ToolCraftingEvent.fireEvent(stack, null, components);
        } catch (TinkerGuiException e) {
            throw new ArtifactType.BuildingException("Armour building produced error: %s", e.getMessage());
        } finally {
            GameStagesHooks.INSTANCE.endBypass();
        }
        ItemStack stackPreMods = stack.copy();

        // add additional free modifiers
        for (int i = spec.freeMods; i > 0; i--) {
            TinkerModifiers.modCreative.apply(stack);
        }

        // apply modifiers
        ArtifactTypeTool.applyModifiers(spec.modifiers, stack);

        // finalize the tool
        try {
            GameStagesHooks.INSTANCE.startBypass();
            TinkerCraftingEvent.ToolModifyEvent.fireEvent(stack, null, stackPreMods.copy());
            // apply the artifact modifier after modify event because otherwise the modifier would cancel the event
            TconEvoTraits.MOD_ARTIFACT.apply(stack);
            NBTTagCompound tag = TagUtil.getTagSafe(stack);
            ArmorBuilder.rebuildArmor(tag, armourType);
            stack.setTagCompound(tag);
        } catch (TinkerGuiException e) {
            throw new ArtifactType.BuildingException("Armour modification produced error: %s", e.getMessage());
        } finally {
            GameStagesHooks.INSTANCE.endBypass();
        }

        // add lore and other NBT
        ArtifactTypeTool.addExtraItemData(stack, spec.lore, spec.dataTag);

        return stack; // done!
    }

}
