package xyz.phanta.tconevo.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.phanta.tconevo.TconEvoMod;

import javax.annotation.Nullable;

public class SPacketEntitySpecialEffect implements IMessage {

    private int entityId;
    private EffectType type;

    public SPacketEntitySpecialEffect() {
        // NO-OP
    }

    public SPacketEntitySpecialEffect(int entityId, EffectType type) {
        this.entityId = entityId;
        this.type = type;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(entityId).writeInt(type.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.type = EffectType.VALUES[buf.readInt()];
    }

    public static class Handler implements IMessageHandler<SPacketEntitySpecialEffect, IMessage> {

        @Nullable
        @Override
        public IMessage onMessage(SPacketEntitySpecialEffect message, MessageContext ctx) {
            //noinspection Convert2Lambda
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    Entity entity = Minecraft.getMinecraft().world.getEntityByID(message.entityId);
                    if (entity != null) {
                        TconEvoMod.PROXY.playEntityEffect(entity, message.type);
                    }
                }
            });
            return null;
        }

    }

    public enum EffectType {

        ENTROPY_BURST,
        FLUX_BURN,
        CHAOS_BURST;

        public static EffectType[] VALUES = values();

    }

}
