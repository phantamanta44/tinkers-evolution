package xyz.phanta.tconevo.trait.base;

import io.github.phantamanta44.libnine.util.ImpossibilityRealizedException;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.IModifier;

public interface IncrementalModifier extends IModifier {

    String getBaseIdentifier();

    int getLevelIncrement();

    int getLevelMaximum();

    default LevelCombiner getLevelCombiner() {
        return LevelCombiner.MAX;
    }

    default void applyEffectIncremental(NBTTagCompound rootTag, int prevLevel, int newLevel) {
        // NO-OP
    }

    enum LevelCombiner {

        SUM, MAX;

        public int apply(int a, int b) {
            switch (this) {
                case SUM:
                    return a + b;
                case MAX:
                    return Math.max(a, b);
                default:
                    throw new ImpossibilityRealizedException();
            }
        }

    }

}
