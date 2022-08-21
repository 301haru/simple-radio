package me.haru301.simpleradio.network.packet;

import me.haru301.simpleradio.SimpleRadio;
import me.haru301.simpleradio.init.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

//TO CLIENT
public class SyncServerConfigPacket
{
    private short chsize;

    public SyncServerConfigPacket(short chsize)
    {
        this.chsize = chsize;
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeShort(chsize);
    }

    //decode
    public SyncServerConfigPacket(PacketBuffer buffer)
    {
        this.chsize = buffer.readShort();
    }

    public static void handle(SyncServerConfigPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    handle(msg));
        });
        ctx.get().setPacketHandled(true);
    }

    private static void handle(SyncServerConfigPacket msg)
    {
        SimpleRadio.CH_SIZE = msg.chsize;
    }
}
