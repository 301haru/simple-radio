package me.haru301.simpleradio.client;

import me.haru301.simpleradio.SimpleRadio;
import me.haru301.simpleradio.client.gui.Gui;
import me.haru301.simpleradio.item.RadioItem;
import me.haru301.simpleradio.network.PacketHandler;
import me.haru301.simpleradio.network.packet.PTTOffPacket;
import me.haru301.simpleradio.network.packet.PTTOnPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
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
        KeyBinds.register();
        mc = Minecraft.getInstance();
        isPTT=false;
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if(event.phase == TickEvent.Phase.START){
            ItemStack itemStack = event.player.getHeldItemMainhand();
            if(itemStack.getItem() instanceof RadioItem)
            {
                Short channel = RadioItem.getChannel(itemStack);
                if(KeyBinds.PTT_KEY.isKeyDown() && !isPTT)
                {
                    PacketHandler.INSTANCE.sendToServer(new PTTOnPacket(channel));
                    isPTT=true;
                }
                else if(!KeyBinds.PTT_KEY.isKeyDown() && isPTT)
                {
                    PacketHandler.INSTANCE.sendToServer(new PTTOffPacket(channel));
                    isPTT=false;
                }
            }
        }
    }
}
