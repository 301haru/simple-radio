package me.haru301.simpleradio.network.packet;

import me.haru301.simpleradio.RadioChannel;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

//TO SERVER
public class PlayerDisconnectRadioPacket
{

    public PlayerDisconnectRadioPacket()
    {
    }

    public void encode(PacketBuffer buffer)
    {
    }

    //decode
    public PlayerDisconnectRadioPacket(PacketBuffer buffer)
    {
    }

    public static void handle(PlayerDisconnectRadioPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            RadioChannel.removePlayerFromChannel(sender);
        });
        ctx.get().setPacketHandled(true);
    }
}
