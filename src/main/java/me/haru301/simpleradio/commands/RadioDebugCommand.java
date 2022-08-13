package me.haru301.simpleradio.commands;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import me.haru301.simpleradio.RadioChannel;
import me.haru301.simpleradio.SimpleRadio;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import org.apache.commons.io.IOUtils;

import java.net.URL;
import java.util.List;
import java.util.UUID;

public class RadioDebugCommand
{
    public RadioDebugCommand(CommandDispatcher<CommandSource> dispatcher)
    {
        LiteralArgumentBuilder<CommandSource> builder =Commands.literal("radio");

        builder.then(Commands.argument("channel", IntegerArgumentType.integer()).executes((source) -> {
            int channel = IntegerArgumentType.getInteger(source, "channel");
            ServerPlayerEntity p = source.getSource().asPlayer();
            if(channel> SimpleRadio.CH_SIZE)
            {
                p.sendMessage(new StringTextComponent("채널 번호: 1~"+SimpleRadio.CH_SIZE+" 까지"), Util.DUMMY_UUID);
                return 1;
            }
            List<ServerPlayerEntity> uuid = RadioChannel.getPlayerFromChannel((short)channel);
            p.sendMessage(new StringTextComponent("<채널 "+channel+" 접속 현황>"), Util.DUMMY_UUID);
            for(ServerPlayerEntity a : uuid)
                p.sendMessage(new StringTextComponent("- " + a.getName().getString() + " ("+a.getUniqueID()+")"), Util.DUMMY_UUID);
            return 1;
        }));

        dispatcher.register(builder);
    }
}
