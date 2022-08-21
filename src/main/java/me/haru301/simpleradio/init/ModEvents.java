package me.haru301.simpleradio.init;

import me.haru301.simpleradio.SimpleRadio;
import me.haru301.simpleradio.client.gui.OverlayHandler;
import me.haru301.simpleradio.commands.RadioDebugCommand;
import me.haru301.simpleradio.config.ModConfigs;
import me.haru301.simpleradio.network.PacketHandler;
import me.haru301.simpleradio.network.packet.SyncServerConfigPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkDirection;

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

    @SubscribeEvent
    public static void onLogIn(PlayerEvent.PlayerLoggedInEvent event)
    {
        ServerPlayerEntity sp = (ServerPlayerEntity) event.getEntity();
        PacketHandler.INSTANCE.sendTo(new SyncServerConfigPacket(ModConfigs.CHANNEL_SIZE.get().shortValue()), sp.connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
        SimpleRadio.CH_SIZE = ModConfigs.CHANNEL_SIZE.get().shortValue();
    }
}
