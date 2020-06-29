package xyz.phanta.tconevo.integration.naturalabsorption;

import fathertoast.naturalabsorption.ModObjects;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import xyz.phanta.tconevo.util.Reflected;

import javax.annotation.Nullable;
import java.util.Optional;

@Reflected
public class NaturalAbsorptionHooksImpl implements NaturalAbsorptionHooks {

    @Override
    public Optional<ItemStack> getItemAbsorptionBook() {
        return Optional.of(new ItemStack(ModObjects.BOOK_ABSORPTION));
    }

    @Nullable
    @Override
    public Enchantment getEnchAbsorption() {
        return ModObjects.ENCHANTMENT_ABSORPTION;
    }

}
