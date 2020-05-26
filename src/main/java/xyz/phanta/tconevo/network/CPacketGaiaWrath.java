package xyz.phanta.tconevo.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import slimeknights.tconstruct.library.utils.ToolHelper;
import xyz.phanta.tconevo.constant.NameConst;
import xyz.phanta.tconevo.integration.botania.BotaniaHooks;
import xyz.phanta.tconevo.util.ToolUtils;

import javax.annotation.Nullable;

@SuppressWarnings("NotNullFieldNotInitialized")
public class CPacketGaiaWrath implements IMessage {

    private EnumHand hand;

    public CPacketGaiaWrath() {
        // NO-OP
    }

    public CPacketGaiaWrath(EnumHand hand) {
        this.hand = hand;
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte((byte)hand.ordinal());
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        hand = buf.readByte() == 0 ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
    }

    public static class Handler implements IMessageHandler<CPacketGaiaWrath, IMessage> {

        @Nullable
        @Override
        public IMessage onMessage(CPacketGaiaWrath message, MessageContext ctx) {
            EntityPlayer player = ctx.getServerHandler().player;
            //noinspection Convert2Lambda
            ctx.getServerHandler().server.addScheduledTask(new Runnable() {
                @Override
                public void run() {
                    if (player.getCooledAttackStrength(0.5F) >= 0.95) {
                        ItemStack stack = player.getHeldItem(message.hand);
                        if (!stack.isEmpty() && ToolUtils.hasTrait(stack, NameConst.TRAIT_GAIA_WRATH)) {
                            ToolHelper.damageTool(stack, 1, player);
                            BotaniaHooks.INSTANCE.spawnGaiaWrathBeam(player, message.hand);
                        }
                    }
                }
            });
            return null;
        }

    }

}
