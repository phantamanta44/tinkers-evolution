package xyz.phanta.tconevo.integration.avaritia;

import morph.avaritia.init.ModItems;
import morph.avaritia.util.TextUtils;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;
import io.github.phantamanta44.libnine.util.nullity.Reflected;

import java.util.Optional;

@Reflected
public class AvaritiaHooksImpl implements AvaritiaHooks {

    @Override
    public Optional<ItemStack> getItemNeutronPile() {
        return Optional.of(ItemHandlerHelper.copyStackWithSize(ModItems.neutron_pile, 1));
    }

    @Override
    public String formatRainbowText(String text) {
        return TextUtils.makeFabulous(text);
    }

}
