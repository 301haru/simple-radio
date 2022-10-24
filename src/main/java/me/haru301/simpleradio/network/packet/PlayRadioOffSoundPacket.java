package me.haru301.simpleradio.network.packet;

import me.haru301.simpleradio.init.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

//TO CLIENT
public class PlayRadioOffSoundPacket
{

    public PlayRadioOffSoundPacket()
    {
    }

    public void encode(PacketBuffer buffer)
    {
    }

    //decode
    public PlayRadioOffSoundPacket(PacketBuffer buffer)
    {
    }

    public static void handle(PlayRadioOffSoundPacket msg, Supplier<NetworkEvent.Context> ctx)
    {
        ctx.get().enqueueWork(() -> {
            DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
                            Minecraft.getInstance().player.playSound(ModSounds.RADIO_OFF.get(), SoundCategory.NEUTRAL, 1, 1));
        });
        ctx.get().setPacketHandled(true);
    }
}
