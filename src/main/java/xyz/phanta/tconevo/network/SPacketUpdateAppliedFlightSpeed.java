package xyz.phanta.tconevo.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import xyz.phanta.tconevo.handler.FlightSpeedHandler;

import javax.annotation.Nullable;

public class SPacketUpdateAppliedFlightSpeed implements IMessage {

    private float speed;

    public SPacketUpdateAppliedFlightSpeed(float speed) {
        this.speed = speed;
    }

    public SPacketUpdateAppliedFlightSpeed() {
        // NO-OP
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(speed);
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        speed = buf.readFloat();
    }

    public static class Handler implements IMessageHandler<SPacketUpdateAppliedFlightSpeed, IMessage> {

        @Nullable
        @Override
        public IMessage onMessage(SPacketUpdateAppliedFlightSpeed message, MessageContext ctx) {
            //noinspection Convert2Lambda
            Minecraft.getMinecraft().addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    EntityPlayerSP player = Minecraft.getMinecraft().player;
                    if (player != null) {
                        FlightSpeedHandler.setCurrentAppliedSpeed(player, message.speed);
                    }
                }
            });
            return null;
        }

    }

}
