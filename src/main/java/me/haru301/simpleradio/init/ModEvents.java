package me.haru301.simpleradio.init;

import me.haru301.simpleradio.SimpleRadio;
import me.haru301.simpleradio.client.gui.OverlayHandler;
import me.haru301.simpleradio.commands.RadioDebugCommand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimpleRadio.MOD_ID)
public class ModEvents
{
    @SubscribeEvent
    public static void onCommandInit(RegisterCommandsEvent event)
    {
        new RadioDebugCommand(event.getDispatcher());
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onOverlayRender(RenderGameOverlayEvent.Post event)
    {
        OverlayHandler.onRender(event.getMatrixStack());
    }
}
