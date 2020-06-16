package xyz.phanta.tconevo.trait;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.constant.NameConst;

import javax.annotation.Nullable;

public class TraitJuggernaut extends AbstractTrait {

    public TraitJuggernaut() {
        super(NameConst.TRAIT_JUGGERNAUT, 0x518a7f);
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        float bonusDamage = damage * Calculator.get().calculateBonusDamage(player.getHealth());
        return bonusDamage > 0F ? (newDamage + bonusDamage) : newDamage;
    }

    // slightly optimizes calculation of bonus damage by caching log values
    private static abstract class Calculator {

        @Nullable
        private static Calculator cached = null;

        static Calculator get() {
            float healthBase = (float)TconEvoConfig.general.traitJuggernautHealthBase;
            float threshFactor = (float)TconEvoConfig.general.traitJuggernautThresholdFactor;
            float damageMult = (float)TconEvoConfig.general.traitJuggernautDamageMultiplier;
            if (cached == null || cached.healthBase != healthBase
                    || cached.threshFactor != threshFactor || cached.damageMult != damageMult) {
                cached = threshFactor <= 1F ? new Linear(healthBase, threshFactor, damageMult)
                        : new Logarithmic(healthBase, threshFactor, damageMult);
            }
            return cached;
        }

        final float healthBase, threshFactor, damageMult;

        protected Calculator(float healthBase, float threshFactor, float damageMult) {
            this.healthBase = healthBase;
            this.threshFactor = threshFactor;
            this.damageMult = damageMult;
        }

        abstract float calculateBonusDamage(float health);

        private static class Logarithmic extends Calculator {

            private final float logHealthBase, logThreshFactor;

            Logarithmic(float healthBase, float threshFactor, float damageMult) {
                super(healthBase, threshFactor, damageMult);
                this.logHealthBase = (float)Math.log(healthBase);
                this.logThreshFactor = (float)Math.log(threshFactor);
            }

            @Override
            float calculateBonusDamage(float health) {
                float result = damageMult * (((float)Math.log(health) - logHealthBase) / logThreshFactor + 1);
                return Float.isFinite(result) ? result : 0F; // might as well be a little careful here
            }

        }

        private static class Linear extends Calculator {

            Linear(float healthBase, float threshFactor, float damageMult) {
                super(healthBase, threshFactor, damageMult);
            }

            @Override
            float calculateBonusDamage(float health) {
                return damageMult * (health / healthBase);
            }

        }

    }

}
