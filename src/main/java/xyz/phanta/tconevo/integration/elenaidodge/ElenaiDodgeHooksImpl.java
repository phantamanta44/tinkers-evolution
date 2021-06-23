package xyz.phanta.tconevo.integration.elenaidodge;

import com.elenai.elenaidodge2.init.EnchantmentInit;
import com.elenai.elenaidodge2.init.ItemInit;
import com.elenai.elenaidodge2.init.PotionInit;
import io.github.phantamanta44.libnine.util.nullity.Reflected;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;

import javax.annotation.Nullable;
import java.util.Optional;

@Reflected
public class ElenaiDodgeHooksImpl implements ElenaiDodgeHooks {

    @Override
    public Optional<ItemStack> getItemFeatheryIron() {
        return Optional.of(new ItemStack(ItemInit.IRON_FEATHER, 1));
    }

    @Override
    public Optional<ItemStack> getItemFeatheryGold() {
        return Optional.of(new ItemStack(ItemInit.GOLDEN_FEATHER, 1));
    }

    @Nullable
    @Override
    public Enchantment getEnchLightweight() {
        return EnchantmentInit.LIGHTWEIGHT;
    }

    @Nullable
    @Override
    public Potion getPotionWeight() {
        return PotionInit.WEIGHT_EFFECT;
    }

}
