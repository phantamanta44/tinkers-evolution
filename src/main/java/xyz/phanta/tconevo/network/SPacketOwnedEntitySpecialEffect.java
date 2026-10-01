package xyz.phanta.tconevo.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.phanta.tconevo.TconEvoMod;

import javax.annotation.Nullable;

@SuppressWarnings("NotNullFieldNotInitialized")
public class SPacketOwnedEntitySpecialEffect implements IMessage {

    private int ownerId, entityId;
    private EffectType type;

    public SPacketOwnedEntitySpecialEffect() {
        // NO-OP
    }

    public SPacketOwnedEntitySpecialEffect(int ownerId, int entityId, EffectType type) {
        this.ownerId = ownerId;
        this.entityId = entityId;
        this.type = type;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        new PacketBuffer(buf).writeVarInt(ownerId).writeVarInt(entityId).writeByte(type.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        final PacketBuffer pb = new PacketBuffer(buf);
        this.ownerId = pb.readVarInt();
        this.entityId = pb.readVarInt();
        this.type = EffectType.VALUES[pb.readByte()];
    }

    public static class Handler implements IMessageHandler<SPacketOwnedEntitySpecialEffect, IMessage> {

        @Nullable
        @Override
        public IMessage onMessage(SPacketOwnedEntitySpecialEffect message, MessageContext ctx) {
            //noinspection Convert2Lambda
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    final World world = Minecraft.getMinecraft().world;
                    if (world == null) return;
                    final Entity owner = world.getEntityByID(message.ownerId);
                    if (owner == null) return;
                    final Entity entity = world.getEntityByID(message.entityId);
                    if (entity == null) return;
                    TconEvoMod.PROXY.playOwnedEntityEffect(owner, entity, message.type);
                }
            });
            return null;
        }

    }

    public enum EffectType {

        MUSOU_NO_HITOTACHI, MANA_STEAL;

        public static final EffectType[] VALUES = values();

    }

}
