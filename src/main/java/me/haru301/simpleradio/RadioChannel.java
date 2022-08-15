package me.haru301.simpleradio;

import me.haru301.simpleradio.init.ModSounds;
import me.haru301.simpleradio.network.PacketHandler;
import me.haru301.simpleradio.network.packet.PlayRadioOffSoundPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.network.NetworkDirection;

import java.util.*;

//Server Sided
public class RadioChannel
{
    private static HashMap<ServerPlayerEntity, Short> players = new HashMap<>();

    public static HashMap<Short, ServerPlayerEntity> ptt = new HashMap<>();

    public static boolean isPTTEmpty(Short channel)
    {
        return !ptt.containsKey(channel);
    }

    public static void addPTT(Short channel, ServerPlayerEntity uuid)
    {
        ptt.put(channel,uuid);
    }

    public static void removePTT(Short channel, ServerPlayerEntity uuid)
    {
        ptt.remove(channel, uuid);
        for(ServerPlayerEntity p : getPlayerFromChannel(channel))
            PacketHandler.INSTANCE.sendTo(new PlayRadioOffSoundPacket(), p.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
    }

    public static boolean isPTTValid(Short channel, ServerPlayerEntity uuid)
    {
        if(ptt.get(channel)==null) return false;
        return ptt.get(channel).equals(uuid);
    }

    public static boolean hasPlayerInPTT(ServerPlayerEntity p)
    {
        for(short key : ptt.keySet())
        {
            if(ptt.get(key).equals(p))
                return true;
        }
        return false;
    }

    public static boolean hasPlayerInPTT(ServerPlayerEntity p, short channel)
    {
        return ptt.get(channel).equals(p);
    }

    public static void addPlayerToChannel(ServerPlayerEntity player, short channel)
    {
        players.put(player, channel);
    }

    public static void removePlayerFromChannel(ServerPlayerEntity player)
    {
        players.remove(player);
    }

    public static boolean hasPlayer(ServerPlayerEntity player)
    {
        return players.containsKey(player);
    }

    public static List<ServerPlayerEntity> getPlayerFromChannel(short channel)
    {
        List<ServerPlayerEntity> serverPlayers = new ArrayList<>();
        for(ServerPlayerEntity key : players.keySet())
        {
            short value = players.get(key);
            if(value==channel)
                serverPlayers.add(key);
        }
        return serverPlayers;
    }
}
