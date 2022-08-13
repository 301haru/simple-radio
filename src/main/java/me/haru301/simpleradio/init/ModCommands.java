package me.haru301.simpleradio.init;

import me.haru301.simpleradio.SimpleRadio;
import me.haru301.simpleradio.commands.RadioDebugCommand;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;

@Mod.EventBusSubscriber(modid = SimpleRadio.MOD_ID)
public class ModCommands
{
    @SubscribeEvent
    public static void onCommandInit(RegisterCommandsEvent event)
    {
        new RadioDebugCommand(event.getDispatcher());
    }
}
