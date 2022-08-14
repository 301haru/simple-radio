package me.haru301.simpleradio.network.packet;

import me.haru301.simpleradio.init.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

//TO CLIENT
public class PlayRadioUnableSoundPacket
{
    public PlayRadioUnableSoundPacket()
    {
    }

    public void encode(PacketBuffer buffer)
    {
    }

    //decode
    public PlayRadioUnableSoundPacket(PacketBuffer buffer)
    {
    }

    public static void handle(PlayRadioUnableSoundPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                    Minecraft.getInstance().player.playSound(ModSounds.RADIO_UNABLE.get(), 1, 1));
        });
        ctx.get().setPacketHandled(true);
    }
}
