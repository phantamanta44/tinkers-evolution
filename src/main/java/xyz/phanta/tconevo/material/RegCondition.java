package xyz.phanta.tconevo.material;

import io.github.phantamanta44.libnine.util.helper.OreDictUtils;
import net.minecraftforge.fml.common.Loader;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import xyz.phanta.tconevo.TconEvoConfig;

public interface RegCondition {

    boolean isSatisfied();

    class ModLoaded implements RegCondition {

        private final String[] modIds;

        public ModLoaded(String[] modIds) {
            this.modIds = modIds;
        }

        @Override
        public boolean isSatisfied() {
            for (String modId : modIds) {
                if (Loader.isModLoaded(modId)) {
                    return true;
                }
            }
            return false;
        }

    }

    class OreDictExists implements RegCondition {

        private final String[] oreKeys;

        public OreDictExists(String[] oreKeys) {
            this.oreKeys = oreKeys;
        }

        @Override
        public boolean isSatisfied() {
            for (String oreKey : oreKeys) {
                if (OreDictUtils.exists(oreKey)) {
                    return true;
                }
            }
            return false;
        }

    }

    class MaterialVisible implements RegCondition {

        private final Material[] materials;

        public MaterialVisible(Material[] materials) {
            this.materials = materials;
        }

        @Override
        public boolean isSatisfied() {
            for (Material material : materials) {
                if (!material.isHidden()) {
                    return true;
                }
            }
            return false;
        }

    }

    class MaterialCanOverride implements RegCondition {

        private final String matId;

        public MaterialCanOverride(String matId) {
            this.matId = matId;
        }

        @Override
        public boolean isSatisfied() {
            return TconEvoConfig.overrideMaterials || TinkerRegistry.getMaterial(matId) == Material.UNKNOWN;
        }

    }

}
