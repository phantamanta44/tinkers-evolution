package xyz.phanta.tconevo.util;

import io.github.phantamanta44.libnine.util.helper.MirrorUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.ModContainer;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.mantle.util.RecipeMatchRegistry;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.smeltery.AlloyRecipe;
import slimeknights.tconstruct.library.smeltery.ICastingRecipe;
import slimeknights.tconstruct.library.smeltery.MeltingRecipe;
import slimeknights.tconstruct.library.traits.ITrait;

import javax.annotation.Nullable;
import java.util.*;

public class TconReflect {

    private static final Map<String, ModContainer> materialRegisteredByMod = MirrorUtils
            .<Map<String, ModContainer>>reflectField(TinkerRegistry.class, "materialRegisteredByMod").get(null);
    private static final Map<String, Map<String, ModContainer>> statRegisteredByMod = MirrorUtils
            .<Map<String, Map<String, ModContainer>>>reflectField(TinkerRegistry.class, "statRegisteredByMod").get(null);
    private static final Map<String, Map<String, ModContainer>> traitRegisteredByMod = MirrorUtils
            .<Map<String, Map<String, ModContainer>>>reflectField(TinkerRegistry.class, "traitRegisteredByMod").get(null);
    private static final Map<String, Material> materials = MirrorUtils
            .<Map<String, Material>>reflectField(TinkerRegistry.class, "materials").get(null);
    private static final List<MeltingRecipe> meltingRegistry = MirrorUtils
            .<List<MeltingRecipe>>reflectField(TinkerRegistry.class, "meltingRegistry").get(null);
    private static final List<ICastingRecipe> tableCastRegistry = MirrorUtils
            .<List<ICastingRecipe>>reflectField(TinkerRegistry.class, "tableCastRegistry").get(null);
    private static final List<ICastingRecipe> basinCastRegistry = MirrorUtils
            .<List<ICastingRecipe>>reflectField(TinkerRegistry.class, "basinCastRegistry").get(null);
    private static final List<AlloyRecipe> alloyRegistry = MirrorUtils
            .<List<AlloyRecipe>>reflectField(TinkerRegistry.class, "alloyRegistry").get(null);
    private static final Set<String> cancelledMaterials = MirrorUtils
            .<Set<String>>reflectField(TinkerRegistry.class, "cancelledMaterials").get(null);

    private static final MirrorUtils.IField<PriorityQueue<RecipeMatch>> fRecipeMatchRecipe_items = MirrorUtils.
            reflectField(RecipeMatchRegistry.class, "items");
    private static final MirrorUtils.IField<List<ItemStack>> fOredict_oredictEntry = MirrorUtils
            .reflectField(RecipeMatch.Oredict.class, "oredictEntry");
    private static final MirrorUtils.IField<Map<String, List<ITrait>>> fMaterial_traits = MirrorUtils
            .reflectField(Material.class, "traits");

    public static void overrideMaterialOwnerMod(Material material, Object modObj) {
        materialRegisteredByMod.put(material.identifier, FMLCommonHandler.instance().findContainerFor(modObj));
    }

    @Nullable
    public static ModContainer getStatOwnerMod(Material material, String statKey) {
        Map<String, ModContainer> statOwners = statRegisteredByMod.get(material.identifier);
        return statOwners != null ? statOwners.get(statKey) : null;
    }

    public static void overrideStatOwnerMod(Material material, String statKey, ModContainer modCont) {
        statRegisteredByMod.computeIfAbsent(material.identifier, k -> new HashMap<>()).put(statKey, modCont);
    }

    @Nullable
    public static ModContainer getTraitOwnerMod(Material material, ITrait trait) {
        Map<String, ModContainer> traitOwners = traitRegisteredByMod.get(material.identifier);
        return traitOwners != null ? traitOwners.get(trait.getIdentifier()) : null;
    }

    public static void overrideTraitOwnerMod(Material material, ITrait trait, ModContainer modCont) {
        traitRegisteredByMod.computeIfAbsent(material.identifier, k -> new HashMap<>())
                .put(trait.getIdentifier(), modCont);
    }

    public static void prioritizeMaterial(Material material) {
       if (materials instanceof LinkedHashMap) {
           JReflect.moveLinkedHashMapEntryToFront((LinkedHashMap<String, Material>)materials, material.identifier);
       }
    }

    public static void removeMaterial(String identifier) {
        materials.remove(identifier);
    }

    public static void uncancelMaterial(String identifier) {
        cancelledMaterials.remove(identifier);
    }

    public static ListIterator<MeltingRecipe> iterateMeltingRecipes() {
        return meltingRegistry.listIterator();
    }

    public static ListIterator<ICastingRecipe> iterateTableCastRecipes() {
        return tableCastRegistry.listIterator();
    }

    public static ListIterator<ICastingRecipe> iterateBasinCastRecipes() {
        return basinCastRegistry.listIterator();
    }

    public static ListIterator<AlloyRecipe> iterateAlloyRecipes() {
        return alloyRegistry.listIterator();
    }

    public static PriorityQueue<RecipeMatch> getItems(RecipeMatchRegistry recipeRegistry) {
        return fRecipeMatchRecipe_items.get(recipeRegistry);
    }

    public static List<ItemStack> getOreEntries(RecipeMatch.Oredict recipeMatch) {
        return fOredict_oredictEntry.get(recipeMatch);
    }

    public static Map<String, List<ITrait>> getTraits(Material material) {
        return fMaterial_traits.get(material);
    }

}
