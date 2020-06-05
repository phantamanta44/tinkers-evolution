package xyz.phanta.tconevo.capability;

import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.integration.astralsorcery.AstralConstellation;

public interface AstralAttunable {

    boolean canAttune();

    void copyUnattunedProperties(ItemStack tuned);

    void attune(AstralConstellation constellation);

    class Noop implements AstralAttunable {

        @Override
        public boolean canAttune() {
            return false;
        }

        @Override
        public void copyUnattunedProperties(ItemStack tuned) {
            // NO-OP
        }

        @Override
        public void attune(AstralConstellation constellation) {
            // NO-OP
        }

    }

}
