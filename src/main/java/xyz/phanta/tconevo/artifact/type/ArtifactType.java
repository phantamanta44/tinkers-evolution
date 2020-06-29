package xyz.phanta.tconevo.artifact.type;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

public interface ArtifactType<T> {

    String ARTIFACT_FMT = TextFormatting.YELLOW.toString() + TextFormatting.UNDERLINE.toString();
    String LORE_FMT = TextFormatting.DARK_PURPLE.toString() + TextFormatting.ITALIC.toString();

    T parseArtifactSpec(JsonObject dto) throws BuildingException;

    ItemStack buildArtifact(T spec) throws BuildingException;

    class BuildingException extends Exception {

        public BuildingException(String message) {
            super(message);
        }

        public BuildingException(String messageFormat, Object... args) {
            this(String.format(messageFormat, args));
        }

    }

}
