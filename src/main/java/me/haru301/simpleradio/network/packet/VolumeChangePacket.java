package me.haru301.simpleradio.network.packet;

import me.haru301.simpleradio.RadioChannel;
import me.haru301.simpleradio.item.RadioItem;
import me.haru301.simpleradio.network.PacketHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static me.haru301.simpleradio.RadioChannel.getPlayerFromChannel;

//TO SERVER
public class VolumeChangePacket
{
    private short volume;

    public VolumeChangePacket(short volume)
    {
        this.volume = volume;
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeShort(volume);
    }

    //decode
    public VolumeChangePacket(PacketBuffer buffer)
    {
        this.volume = buffer.readShort();
    }

    public static void handle(VolumeChangePacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            short volume = msg.volume;
            RadioItem.setVolume(sender.getHeldItemMainhand(), volume);
        });
        ctx.get().setPacketHandled(true);
    }
}
