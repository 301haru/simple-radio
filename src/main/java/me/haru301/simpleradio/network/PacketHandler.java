package me.haru301.simpleradio.network;

import me.haru301.simpleradio.SimpleRadio;
import me.haru301.simpleradio.network.packet.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler
{
    public static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel INSTANCE = NetworkRegistry
            .newSimpleChannel(new ResourceLocation(SimpleRadio.MOD_ID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);

    public static void init()
    {
        int id = 0;
        INSTANCE.messageBuilder(PlayRadioOffSoundPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).encoder(PlayRadioOffSoundPacket::encode).decoder(PlayRadioOffSoundPacket::new).consumer(PlayRadioOffSoundPacket::handle).add();
        INSTANCE.messageBuilder(PlayRadioOnSoundPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).encoder(PlayRadioOnSoundPacket::encode).decoder(PlayRadioOnSoundPacket::new).consumer(PlayRadioOnSoundPacket::handle).add();
        INSTANCE.messageBuilder(PlayRadioUnableSoundPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).encoder(PlayRadioUnableSoundPacket::encode).decoder(PlayRadioUnableSoundPacket::new).consumer(PlayRadioUnableSoundPacket::handle).add();
        INSTANCE.messageBuilder(PTTOverlayPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).encoder(PTTOverlayPacket::encode).decoder(PTTOverlayPacket::new).consumer(PTTOverlayPacket::handle).add();

        INSTANCE.messageBuilder(SyncServerConfigPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).encoder(SyncServerConfigPacket::encode).decoder(SyncServerConfigPacket::new).consumer(SyncServerConfigPacket::handle).add();

        /*INSTANCE.messageBuilder(RadioStatePacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).encoder(RadioStatePacket::encode).decoder(RadioStatePacket::new).consumer(RadioStatePacket::handle).add();
        INSTANCE.messageBuilder(RadioUUIDPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT).encoder(RadioUUIDPacket::encode).decoder(RadioUUIDPacket::new).consumer(RadioUUIDPacket::handle).add();
*/

        INSTANCE.messageBuilder(PlayerConnectRadioPacket.class, id++, NetworkDirection.PLAY_TO_SERVER).encoder(PlayerConnectRadioPacket::encode).decoder(PlayerConnectRadioPacket::new).consumer(PlayerConnectRadioPacket::handle).add();
        INSTANCE.messageBuilder(PlayerDisconnectRadioPacket.class, id++, NetworkDirection.PLAY_TO_SERVER).encoder(PlayerDisconnectRadioPacket::encode).decoder(PlayerDisconnectRadioPacket::new).consumer(PlayerDisconnectRadioPacket::handle).add();
        INSTANCE.messageBuilder(PTTOnPacket.class, id++, NetworkDirection.PLAY_TO_SERVER).encoder(PTTOnPacket::encode).decoder(PTTOnPacket::new).consumer(PTTOnPacket::handle).add();
        INSTANCE.messageBuilder(PTTOffPacket.class, id++, NetworkDirection.PLAY_TO_SERVER).encoder(PTTOffPacket::encode).decoder(PTTOffPacket::new).consumer(PTTOffPacket::handle).add();
        INSTANCE.messageBuilder(VolumeChangePacket.class, id++, NetworkDirection.PLAY_TO_SERVER).encoder(VolumeChangePacket::encode).decoder(VolumeChangePacket::new).consumer(VolumeChangePacket::handle).add();

    }
}

