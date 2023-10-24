package xyz.phanta.tconevo.artifact.type;

import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import xyz.phanta.tconevo.TconEvoConsts;
import xyz.phanta.tconevo.artifact.Artifact;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ArtifactTypeRegistry {

    private final Map<ResourceLocation, ArtifactType<?>> typeRegistry = new HashMap<>();

    public ArtifactTypeRegistry() {
        // can't use newResourceLocation() because the mod isn't fully-initialized yet
        registerArtifactType(new ResourceLocation(TconEvoConsts.MOD_ID, "tool"), new ArtifactTypeTool());
        registerArtifactType(new ResourceLocation(TconEvoConsts.MOD_ID, "armour"), new ArtifactTypeArmour());
    }

    public void registerArtifactType(ResourceLocation typeId, ArtifactType<?> type) {
        typeRegistry.put(typeId, type);
    }

    @Nullable
    public ArtifactType<?> getArtifactType(ResourceLocation typeId) {
        return typeRegistry.get(typeId);
    }

    @Nullable
    public Artifact<?> parseArtifact(String artifactId, JsonObject dto)
            throws ArtifactType.BuildingException {
        ArtifactType<?> type = getArtifactType(new ResourceLocation(JsonUtils.getString(dto, "type")));
        return type != null ? constructArtifact(artifactId, type, dto) : null;
    }

    private static <T> Artifact<T> constructArtifact(String artifactId, ArtifactType<T> type, JsonObject dto)
            throws ArtifactType.BuildingException {
        return new Artifact<>(artifactId, type, type.parseArtifactSpec(dto), JsonUtils.getInt(dto, "weight"));
    }

}
