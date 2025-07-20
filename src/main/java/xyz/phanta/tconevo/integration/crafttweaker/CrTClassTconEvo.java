package xyz.phanta.tconevo.integration.crafttweaker;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.IAction;
import crafttweaker.annotations.ZenRegister;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import xyz.phanta.tconevo.init.TconEvoPartTypes;
import xyz.phanta.tconevo.material.stats.MagicMaterialStats;

import java.util.NoSuchElementException;

@ZenRegister
@ZenClass("mods.tconevo.TconEvo")
public class CrTClassTconEvo {

    @ZenMethod
    public static void setMagicMaterialStats(String materialId,
                                             int durability, float potency, float range, int harvestLevel) {
        CraftTweakerAPI.apply(new IAction() {
            @Override
            public void apply() {
                Material mat = TinkerRegistry.getMaterial(materialId);
                if (mat == null) {
                    throw new NoSuchElementException("Unknown material: " + materialId);
                }
                if (mat.hasStats(TconEvoPartTypes.MAGIC)) {
                    // probably should fire a MaterialEvent.StatRegisterEvent here, but oh well
                    mat.addStats(new MagicMaterialStats(durability, potency, range, harvestLevel));
                } else {
                    TinkerRegistry.addMaterialStats(
                            mat, new MagicMaterialStats(durability, potency, range, harvestLevel));
                }
            }

            @Override
            public String describe() {
                return String.format("Setting magic material stats for %s to {dur=%d,pot=%f,ran=%f,har=%d}",
                        materialId, durability, potency, range, harvestLevel);
            }
        });
    }

}
