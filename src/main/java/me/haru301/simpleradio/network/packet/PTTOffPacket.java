package me.haru301.simpleradio.network.packet;

import me.haru301.simpleradio.RadioChannel;
import me.haru301.simpleradio.SimpleRadioAddon;
import me.haru301.simpleradio.network.PacketHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
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
                //check if sender actually in PTT hashmap
                if(!RadioChannel.hasPlayerInPTT(sender, channel))
                    return;

                //only remove if uuid matches channel
                RadioChannel.removePTT(channel, sender);
                PacketHandler.INSTANCE.sendTo(new PTTOverlayPacket(false), sender.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
