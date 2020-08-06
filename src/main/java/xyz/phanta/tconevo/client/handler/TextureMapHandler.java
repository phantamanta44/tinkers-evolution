package xyz.phanta.tconevo.client.handler;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;

public class TextureMapHandler {

    public static final TextureMapHandler INSTANCE = new TextureMapHandler();

    @Nullable
    private TextureMap currentlyStitching = null;

    private TextureMapHandler() {
        // NO-OP
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onTextureMapStart(TextureStitchEvent.Pre event) {
        currentlyStitching = event.getMap();
    }

    public void registerSprite(TextureAtlasSprite sprite) {
        if (currentlyStitching != null) {
            currentlyStitching.setTextureEntry(sprite);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onTextureMapEnd(TextureStitchEvent.Post event) {
        currentlyStitching = null;
    }

}
