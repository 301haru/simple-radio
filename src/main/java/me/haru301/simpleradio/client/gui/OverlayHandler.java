package me.haru301.simpleradio.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.haru301.simpleradio.SimpleRadio;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class OverlayHandler
{
    private static final ResourceLocation RADIO_ICON = new ResourceLocation(SimpleRadio.MOD_ID, "textures/icon/icon.png");

    private static boolean pttStatus = false;

    private static Minecraft mc;

    public static void init()
    {
        mc = Minecraft.getInstance();
    }

    public static void setPttStatus(boolean status)
    {
        pttStatus = status;
    }

    public static void onRender(MatrixStack stack)
    {
        if(pttStatus)
            renderIcon(stack, RADIO_ICON);
    }

    private static void renderIcon(MatrixStack stack, ResourceLocation texture)
    {
        stack.push();
        mc.getTextureManager().bindTexture(texture);
        stack.translate(40, mc.getMainWindow().getScaledHeight()-32, 0D);
        Screen.blit(stack, 0, 0, 0, 0, 16, 16, 16, 16);

        //TODO optimize position

        stack.pop();
    }
}
