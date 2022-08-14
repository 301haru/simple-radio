package me.haru301.simpleradio.client;

import me.haru301.simpleradio.SimpleRadio;
import me.haru301.simpleradio.client.gui.OverlayHandler;
import me.haru301.simpleradio.item.RadioItem;
import me.haru301.simpleradio.network.PacketHandler;
import me.haru301.simpleradio.network.packet.PTTOffPacket;
import me.haru301.simpleradio.network.packet.PTTOnPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimpleRadio.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientHandler
{
    private static Minecraft mc;

    private static boolean isPTT;

    public static void init()
    {
        mc = Minecraft.getInstance();
        isPTT=false;


        OverlayHandler.init();
        KeyBinds.register();
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.START){
            ItemStack itemStack = event.player.getHeldItemMainhand();
            short channel = RadioItem.getChannel(itemStack);
            if(KeyBinds.PTT_KEY.isKeyDown() && !isPTT && itemStack.getItem() instanceof RadioItem)
            {
                //SimpleRadio.LOGGER.info("PTTONPacket Sent!");
                PacketHandler.INSTANCE.sendToServer(new PTTOnPacket(channel));
                isPTT=true;
            }
            else if(!KeyBinds.PTT_KEY.isKeyDown() && isPTT)
            {
                //SimpleRadio.LOGGER.info("PTTOFFPacket Sent!");
                PacketHandler.INSTANCE.sendToServer(new PTTOffPacket(channel));
                isPTT=false;
            }
        }
    }
}
