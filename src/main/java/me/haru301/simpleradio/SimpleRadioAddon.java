package me.haru301.simpleradio;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.EventRegistration;
import de.maxhenkel.voicechat.api.events.MicrophonePacketEvent;
import de.maxhenkel.voicechat.api.events.StaticSoundPacketEvent;
import de.maxhenkel.voicechat.api.events.VoicechatServerStoppedEvent;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;
import de.maxhenkel.voicechat.api.opus.OpusEncoder;
import me.haru301.simpleradio.init.ModItems;
import me.haru301.simpleradio.item.RadioItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

@ForgeVoicechatPlugin
public class SimpleRadioAddon implements VoicechatPlugin
{
    private OpusDecoder decoder;
    private OpusEncoder encoder;

    @Override
    public String getPluginId()
    {
        return SimpleRadio.MOD_ID;
    }

    @Override
    public void initialize(VoicechatApi api)
    {
        encoder = api.createEncoder();
        decoder = api.createDecoder();
        SimpleRadio.LOGGER.info("INITIALIZED");
    }

    @Override
    public void registerEvents(EventRegistration regi)
    {
        regi.registerEvent(MicrophonePacketEvent.class, this::onVoice);
        regi.registerEvent(VoicechatServerStoppedEvent.class, this::onServerStop);
    }

    private void onVoice(MicrophonePacketEvent event)
    {
        ServerPlayerEntity sender = (ServerPlayerEntity)event.getSenderConnection().getPlayer().getPlayer();

        //SimpleRadio.LOGGER.info(RadioChannel.ptt.get((short)1));
        //SimpleRadio.LOGGER.info(RadioChannel.getPlayerFromChannel((short)1));

        if(!sender.inventory.hasItemStack(new ItemStack(ModItems.RADIO.get())))
            return;

        VoicechatServerApi api = event.getVoicechat();

        //Can use radio when player is holding
        ItemStack mainHand = sender.getHeldItemMainhand();
        short channel = RadioItem.getChannel(mainHand);

        //SimpleRadio.LOGGER.info(volume + " " + channel);

        if(mainHand.getItem() instanceof RadioItem) {
            //check Player holding Radio PTT key
            if(!RadioChannel.isPTTValid(channel, sender))
                return;

            if(RadioChannel.hasPlayer(sender))
                playStaticSound(api, event, sender, channel);
        }
    }

    private void playStaticSound(VoicechatServerApi api, MicrophonePacketEvent e, ServerPlayerEntity sender, short channel)
    {
        for(ServerPlayerEntity p : RadioChannel.getPlayerFromChannel(channel))
        {
            if(p.equals(sender)) //check if sender
                continue;
            VoicechatConnection con = api.getConnectionOf(p.getUniqueID());
            if(con == null)
                continue;

            short volume = RadioItem.getVolume(p.getHeldItemMainhand());
            short[] audio = decoder.decode(e.getPacket().getOpusEncodedData());
            audio = adjustVolume(audio, volume);

            byte[] audioEncoded = encoder.encode(audio);

            //Send Audio Packet with Sound Data To RadioChannel Conncected Players.
            api.sendStaticSoundPacketTo(con, e.getPacket().staticSoundPacketBuilder().opusEncodedData(audioEncoded).build());
        }
    }

    private short[] adjustVolume(short[] audio, double volume)
    {
        short[] array = new short[audio.length];
        for (int i=0; i<audio.length; i++)
        {
            short audioSample = audio[i];
            array[i] = (short) (audioSample * volume/100);
        }

        return array;
    }

    public void onServerStop(VoicechatServerStoppedEvent event)
    {
        decoder.close();
        encoder.close();
    }
}
