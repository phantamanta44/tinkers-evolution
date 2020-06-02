package xyz.phanta.tconevo.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class InventoryIterator implements Iterator<ItemStack> {

    private final IInventory inventory;
    private int nextSlot = 0;

    public InventoryIterator(IInventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public boolean hasNext() {
        return nextSlot < inventory.getSizeInventory();
    }

    @Override
    public ItemStack next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return inventory.getStackInSlot(nextSlot++);
    }

}
