package xyz.phanta.tconevo.artifact;

import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.artifact.type.ArtifactType;
import xyz.phanta.tconevo.artifact.type.ArtifactTypeRegistry;

import javax.annotation.Nullable;
import java.util.*;

public class ArtifactRegistry {

    private final ArtifactLoader loader = new ArtifactLoader();
    private final ArtifactTypeRegistry typeRegistry = new ArtifactTypeRegistry();
    private final Map<String, Artifact<?>> artifactRegistry = new HashMap<>();
    private boolean artifactsLoaded = false;

    public ArtifactLoader getLoader() {
        return loader;
    }

    public ArtifactTypeRegistry getTypeRegistry() {
        return typeRegistry;
    }

    void registerArtifacts(Collection<Artifact<?>> artifacts) {
        artifactRegistry.clear();
        for (Artifact<?> artifact : artifacts) {
            artifactRegistry.put(artifact.getEntryName(), artifact);
        }
        artifactsLoaded = true;
    }

    public void initArtifacts() {
        if (!TconEvoConfig.artifacts.enabled) {
            return;
        }
        TconEvoMod.LOGGER.info("Initializing artifacts...");
        for (Artifact<?> artifact : artifactRegistry.values()) {
            TconEvoMod.LOGGER.debug("Initializing artifact: {}", artifact.getEntryName());
            try {
                artifact.initialize();
            } catch (ArtifactType.BuildingException e) {
                TconEvoMod.LOGGER.warn("Failed to initialize artifact \"{}\": {}", artifact.getEntryName(), e.getMessage());
            } catch (Exception e) {
                TconEvoMod.LOGGER.warn("Encountered exception while initializing artifact: " + artifact.getEntryName(), e);
            }
        }
    }

    @Nullable
    public Artifact<?> getArtifact(String id) {
        if (!artifactsLoaded) {
            TconEvoMod.LOGGER.error("Artifacts were looked up before being loaded!", new IllegalStateException());
            return null;
        }
        return artifactRegistry.get(id);
    }

    public Set<String> getAllArtifactIds() {
        if (!artifactsLoaded) {
            TconEvoMod.LOGGER.error("Artifacts were looked up before being loaded!", new IllegalStateException());
            return Collections.emptySet();
        }
        return Collections.unmodifiableSet(artifactRegistry.keySet());
    }

    public Collection<Artifact<?>> getAllArtifacts() {
        if (!artifactsLoaded) {
            TconEvoMod.LOGGER.error("Artifacts were looked up before being loaded!", new IllegalStateException());
            return Collections.emptyList();
        }
        return Collections.unmodifiableCollection(artifactRegistry.values());
    }

    public LootCondition getLootCondition(String id) {
        return new ConditionArtifactExists(id);
    }

    private class ConditionArtifactExists implements LootCondition {

        private final String id;

        ConditionArtifactExists(String id) {
            this.id = id;
        }

        @Override
        public boolean testCondition(Random rand, LootContext context) {
            Artifact<?> artifact = getArtifact(id);
            return artifact != null && artifact.isValid();
        }

    }

}
