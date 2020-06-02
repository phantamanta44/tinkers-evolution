package xyz.phanta.tconevo.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ItemHandlerIterator implements Iterator<ItemStack> {

    private final IItemHandler inventory;
    private int nextSlot = 0;

    public ItemHandlerIterator(IItemHandler inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean hasNext() {
        return nextSlot < inventory.getSlots();
    }

    @Override
    public ItemStack next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return inventory.getStackInSlot(nextSlot++);
    }

}
