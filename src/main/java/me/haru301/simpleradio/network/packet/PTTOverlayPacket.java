package me.haru301.simpleradio.network.packet;

import me.haru301.simpleradio.client.gui.OverlayHandler;
import me.haru301.simpleradio.init.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

//TO CLIENT
public class PTTOverlayPacket
{
    private boolean status;

    public PTTOverlayPacket(boolean status)
    {
        this.status = status;
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeBoolean(status);
    }

    //decode
    public PTTOverlayPacket(PacketBuffer buffer)
    {
        this.status = buffer.readBoolean();
    }

    public static void handle(PTTOverlayPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    OverlayHandler.setPttStatus(msg.status));
        });
        ctx.get().setPacketHandled(true);
    }
}
