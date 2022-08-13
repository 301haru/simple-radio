package me.haru301.simpleradio.network.packet;

import me.haru301.simpleradio.RadioChannel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;
import java.util.function.Supplier;

//TO SERVER
public class PTTOffPacket
{
    private short channel;

    public PTTOffPacket(short channel)
    {
        this.channel = channel;
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeShort(channel);
    }

    //decode
    public PTTOffPacket(PacketBuffer buffer)
    {
        this.channel = buffer.readShort();
    }

    public static void handle(PTTOffPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            short channel = msg.channel;
            if(RadioChannel.hasPlayer(sender))
            {
                //if someone using so PTTOnPacket did nothing
                if(!RadioChannel.isPTTEmpty(channel))
                    return;

                //only remove if uuid matches channel
                RadioChannel.removePTT(channel, sender);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
