package me.haru301.simpleradio;

import de.maxhenkel.voicechat.api.*;
import de.maxhenkel.voicechat.api.events.*;
import de.maxhenkel.voicechat.api.opus.OpusDecoder;
import de.maxhenkel.voicechat.api.opus.OpusEncoder;
import me.haru301.simpleradio.init.ModItems;
import me.haru301.simpleradio.item.RadioItem;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;

@ForgeVoicechatPlugin
public class SimpleRadioAddon implements VoicechatPlugin
{

    @Override
    public String getPluginId()
    {
        return SimpleRadio.MOD_ID;
    }

    @Override
    public void initialize(VoicechatApi api)
    {
        SimpleRadio.LOGGER.info("INITIALIZED");
    }

    @Override
    public void registerEvents(EventRegistration regi)
    {
        regi.registerEvent(MicrophonePacketEvent.class, this::onVoice);
        regi.registerEvent(ClientReceiveSoundEvent.StaticSound.class, this::onVoiceReceive);
    }

    private void onVoiceReceive(ClientReceiveSoundEvent.StaticSound event)
    {
        short volume = RadioItem.getVolume(Minecraft.getInstance().player.getHeldItemMainhand());
        event.setRawAudio(adjustVolume(event.getRawAudio(), volume));
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
            if(RadioChannel.hasPlayer(sender))
            {
                //check Player holding Radio PTT key
                if(!RadioChannel.isPTTValid(channel, sender))
                    return;
                playStaticSound(api, event, sender, channel);
            }
        }
    }

    private void playStaticSound(VoicechatServerApi api, MicrophonePacketEvent e, ServerPlayerEntity sender, short channel)
    {
        for(ServerPlayerEntity p : RadioChannel.getPlayerFromChannel(channel))
        {
            //if(p.equals(sender)) //check if sender
            //    continue;
            VoicechatConnection con = api.getConnectionOf(p.getUniqueID());
            if(con == null)
                continue;
            api.sendStaticSoundPacketTo(con, e.getPacket().staticSoundPacketBuilder().build());
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
}
