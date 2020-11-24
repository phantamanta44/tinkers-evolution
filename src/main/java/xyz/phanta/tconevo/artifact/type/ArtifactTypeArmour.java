package xyz.phanta.tconevo.artifact.type;

import com.google.gson.JsonObject;
import io.github.phantamanta44.libnine.util.nbt.ImmutableNbt;
import io.github.phantamanta44.libnine.util.tuple.IPair;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.JsonUtils;
import xyz.phanta.tconevo.integration.conarm.ConArmHooks;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class ArtifactTypeArmour implements ArtifactType<ArtifactTypeArmour.Spec> {

    @Override
    public Spec parseArtifactSpec(JsonObject dto) throws BuildingException {
        //noinspection ConstantConditions
        return new Spec(
                JsonUtils.getString(dto, "name"),
                ArtifactTypeTool.parseLore(dto),
                JsonUtils.getString(dto, "armour"),
                ArtifactTypeTool.parseMaterials(dto),
                JsonUtils.getInt(dto, "free_mods", 0),
                ArtifactTypeTool.parseModifiers(dto),
                JsonUtils.getJsonObject(dto, "data_tag", null));
    }

    @Override
    public ItemStack buildArtifact(Spec spec) throws BuildingException {
        return ConArmHooks.INSTANCE.buildArmourArtifact(spec);
    }

    public static class Spec {

        public final String name;
        public final List<String> lore;
        public final String armourType;
        public final List<String> materials;
        public final int freeMods;
        public final List<IPair<String, Integer>> modifiers;
        @Nullable
        public final ImmutableNbt<NBTTagCompound> dataTag;

        public Spec(String name, List<String> lore, String armourType, List<String> materials,
                    int freeMods, List<IPair<String, Integer>> modifiers, @Nullable JsonObject dataTag) {
            this.name = name;
            this.lore = Collections.unmodifiableList(lore);
            this.armourType = armourType;
            this.materials = Collections.unmodifiableList(materials);
            this.freeMods = freeMods;
            this.modifiers = Collections.unmodifiableList(modifiers);
            this.dataTag = dataTag != null ? ImmutableNbt.parseObject(dataTag) : null;
        }

    }

}
