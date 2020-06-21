package xyz.phanta.tconevo.integration.redstonerepository;

import net.minecraft.item.ItemStack;
import thundr.redstonerepository.init.RedstoneRepositoryEquipment;

import java.util.Optional;

public class RedstoneRepositoryHooksImpl implements RedstoneRepositoryHooks {

    @Override
    public Optional<ItemStack> getItemGelidCapacitor() {
        return Optional.of(new ItemStack(RedstoneRepositoryEquipment.EquipmentInit.itemCapacitorAmulet));
    }

}
