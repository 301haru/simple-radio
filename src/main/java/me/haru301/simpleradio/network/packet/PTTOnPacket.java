package me.haru301.simpleradio.network.packet;

import me.haru301.simpleradio.RadioChannel;
import me.haru301.simpleradio.item.RadioItem;
import me.haru301.simpleradio.network.PacketHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

//TO SERVER
public class PTTOnPacket
{
    private short channel;

    public PTTOnPacket(short channel)
    {
        this.channel = channel;
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeShort(channel);
    }

    //decode
    public PTTOnPacket(PacketBuffer buffer)
    {
        this.channel = buffer.readShort();
    }

    public static void handle(PTTOnPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            short channel = msg.channel;

            if(RadioChannel.hasPlayer(sender))
            {
                if(!(sender.getHeldItemMainhand().getItem() instanceof RadioItem))
                    return;

                if(RadioChannel.isPTTEmpty(channel))
                {
                    RadioChannel.addPTT(channel,sender);
                    PacketHandler.INSTANCE.sendTo(new PTTOverlayPacket(true), sender.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
                }
                else sender.sendMessage(new TranslationTextComponent("someoneusing"), Util.DUMMY_UUID); //TODO
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
