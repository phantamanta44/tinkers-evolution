package xyz.phanta.tconevo.material;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;
import slimeknights.tconstruct.library.materials.Material;

public interface RegCondition {

    boolean isSatisfied();

    class ModLoaded implements RegCondition {

        private final String modId;

        public ModLoaded(String modId) {
            this.modId = modId;
        }

        @Override
        public boolean isSatisfied() {
            return Loader.isModLoaded(modId);
        }

    }

    class OreDictExists implements RegCondition {

        private final String oreKey;

        public OreDictExists(String oreKey) {
            this.oreKey = oreKey;
        }

        @Override
        public boolean isSatisfied() {
            return !OreDictionary.getOres(oreKey, false).isEmpty();
        }

    }

    class MaterialVisible implements RegCondition {

        private final Material material;

        public MaterialVisible(Material material) {
            this.material = material;
        }

        @Override
        public boolean isSatisfied() {
            return !material.isHidden();
        }

    }

}
