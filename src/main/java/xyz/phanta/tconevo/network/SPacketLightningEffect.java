package xyz.phanta.tconevo.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.phanta.tconevo.TconEvoMod;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("NotNullFieldNotInitialized")
public class SPacketLightningEffect implements IMessage {

    private List<Vec3d> positions;

    public SPacketLightningEffect() {
        // NO-OP
    }

    public SPacketLightningEffect(List<Vec3d> positions) {
        this.positions = positions;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeShort(positions.size());
        for (Vec3d pos : positions) {
            buf.writeDouble(pos.x).writeDouble(pos.y).writeDouble(pos.z);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        positions = new ArrayList<>();
        for (int i = buf.readShort(); i > 0; i--) {
            positions.add(new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble()));
        }
    }

    public static class Handler implements IMessageHandler<SPacketLightningEffect, IMessage> {

        @Nullable
        @Override
        public IMessage onMessage(SPacketLightningEffect message, MessageContext ctx) {
            //noinspection Convert2Lambda
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    TconEvoMod.PROXY.playLightningEffect(Minecraft.getMinecraft().player, message.positions);
                }
            });
            return null;
        }

    }

}
