package me.haru301.simpleradio.network.packet;

import me.haru301.simpleradio.RadioChannel;
import me.haru301.simpleradio.init.ModSounds;
import me.haru301.simpleradio.item.RadioItem;
import me.haru301.simpleradio.network.PacketHandler;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static me.haru301.simpleradio.RadioChannel.getPlayerFromChannel;

//TO SERVER
public class PlayerConnectRadioPacket
{
    private short channel;

    public PlayerConnectRadioPacket(short channel)
    {
        this.channel = channel;
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeShort(channel);
    }

    //decode
    public PlayerConnectRadioPacket(PacketBuffer buffer)
    {
        this.channel = buffer.readShort();
    }

    public static void handle(PlayerConnectRadioPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity sender = ctx.get().getSender();
            short channel = msg.channel;

            RadioItem.setChannel(sender.getHeldItemMainhand(), channel);
            RadioChannel.addPlayerToChannel(sender, channel);
            for(ServerPlayerEntity p : getPlayerFromChannel(channel))
                PacketHandler.INSTANCE.sendTo(new PlayRadioOnSoundPacket(), p.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
        });
        ctx.get().setPacketHandled(true);
    }
}
