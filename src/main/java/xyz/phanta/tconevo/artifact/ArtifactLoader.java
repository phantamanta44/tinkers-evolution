package xyz.phanta.tconevo.artifact;

import com.google.gson.JsonObject;
import io.github.phantamanta44.libnine.util.helper.JsonUtils9;
import xyz.phanta.tconevo.TconEvoConfig;
import xyz.phanta.tconevo.TconEvoMod;
import xyz.phanta.tconevo.artifact.type.ArtifactType;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.stream.Collectors;

public class ArtifactLoader {

    @Nullable
    private Path artifactDir;

    public void setArtifactDir(Path artifactDir) {
        if (this.artifactDir != null) {
            throw new IllegalStateException("Artifact directory is already set!");
        }
        this.artifactDir = artifactDir;
    }

    public void loadArtifacts() {
        if (artifactDir == null) {
            throw new IllegalStateException("Artifact directory is not set!");
        }
        if (TconEvoConfig.artifacts.enabled) {
            TconEvoMod.LOGGER.info("Loading artifacts...");
            try {
                TconEvoMod.PROXY.getArtifactRegistry().registerArtifacts(Files.list(artifactDir)
                        .filter(f -> f.getFileName().toString().endsWith(".json"))
                        .map(path -> {
                            TconEvoMod.LOGGER.debug("Loading artifact file: {}", path);
                            String artifactId;
                            try {
                                artifactId = path.getFileName().toString();
                                artifactId = artifactId.substring(0, artifactId.length() - 5); // strip off .json extension
                            } catch (Exception e) { // better safe than sorry
                                TconEvoMod.LOGGER.warn("Encountered exception while parsing artifact ID for file: " + path, e);
                                return null;
                            }
                            try {
                                JsonObject dto = JsonUtils9.PARSER.parse(Files.newBufferedReader(path)).getAsJsonObject();
                                return TconEvoMod.PROXY.getArtifactRegistry().getTypeRegistry().parseArtifact(artifactId, dto);
                            } catch (ArtifactType.BuildingException e) {
                                TconEvoMod.LOGGER.warn("Failed to load artifact \"{}\": {}", artifactId, e.getMessage());
                            } catch (Exception e) {
                                TconEvoMod.LOGGER.warn("Encountered exception while loading artifact: " + artifactId, e);
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList()));
            } catch (IOException e) {
                TconEvoMod.LOGGER.error("Encountered exception while loading artifacts!", e);
            }
        }
    }

}
