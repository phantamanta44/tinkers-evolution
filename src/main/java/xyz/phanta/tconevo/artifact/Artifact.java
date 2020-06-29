package xyz.phanta.tconevo.artifact;

import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import io.github.phantamanta44.libnine.util.TriBool;
import net.minecraft.item.ItemStack;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.artifact.type.ArtifactType;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.Random;

public class Artifact<T> extends LootEntry {

    private final ArtifactType<T> type;
    private final T spec;
    @Nullable
    private ItemStack stack;
    private TriBool valid = TriBool.NONE;

    public Artifact(String id, ArtifactType<T> type, T spec, int weight) {
        super(weight, 0, new LootCondition[] { TconEvoMod.PROXY.getArtifactRegistry().getLootCondition(id) }, id);
        this.type = type;
        this.spec = spec;
    }

    public void initialize() throws ArtifactType.BuildingException {
        if (valid != TriBool.NONE) {
            throw new IllegalStateException("Artifact is already initialized: " + getEntryName());
        }
        try {
            stack = type.buildArtifact(spec);
        } catch (ArtifactType.BuildingException e) {
            valid = TriBool.FALSE;
            throw e;
        }
        valid = TriBool.TRUE;
    }

    public boolean isValid() {
        return stack != null && !stack.isEmpty();
    }

    public ItemStack newStack() {
        if (valid == TriBool.NONE) {
            TconEvoMod.LOGGER.warn("Artifact was used before it was initialized: " + getEntryName(), new IllegalStateException());
            return ItemStack.EMPTY;
        }
        return valid.value ? Objects.requireNonNull(stack).copy() : ItemStack.EMPTY;
    }

    @Override
    public void addLoot(Collection<ItemStack> stacks, Random rand, LootContext context) {
        if (valid == TriBool.NONE) {
            TconEvoMod.LOGGER.warn("Artifact was used before it was initialized: " + getEntryName(), new IllegalStateException());
        } else if (valid.value) {
            stacks.add(Objects.requireNonNull(stack).copy());
        }
    }

    @Override
    protected void serialize(JsonObject json, JsonSerializationContext context) {
        throw new UnsupportedOperationException(); // there's no reason this should ever need to be serialized
    }

}
