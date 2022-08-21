package me.haru301.simpleradio.client.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import me.haru301.simpleradio.SimpleRadio;
import me.haru301.simpleradio.client.gui.widget.VolumeSlider;
import me.haru301.simpleradio.client.gui.widget.ForgeSlider;
import me.haru301.simpleradio.config.ModConfigs;
import me.haru301.simpleradio.item.RadioItem;
import me.haru301.simpleradio.network.PacketHandler;
import me.haru301.simpleradio.network.packet.PlayerConnectRadioPacket;
import me.haru301.simpleradio.network.packet.PlayerDisconnectRadioPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiHandler extends Screen
{
    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(SimpleRadio.MOD_ID, "textures/gui/gui_background.png");

    private ItemStack stack;

    public GuiHandler(ITextComponent titleIn, ItemStack stack)
    {
        super(titleIn);
        this.stack = stack;
    }


    @Override
    protected void init()
    {
        super.init();

        this.stack = Minecraft.getInstance().player.getHeldItemMainhand();

        ForgeSlider channel = new ForgeSlider(this.width/2 -91, (this.height-20)/2 -5, 110, 20, new TranslationTextComponent("gui.simpleradio.channelSlider").appendString(" "), new StringTextComponent(""), 1, SimpleRadio.CH_SIZE, RadioItem.getChannel(stack), 1D, 1, true);
        addButton(channel);

        VolumeSlider volume = new VolumeSlider(this.width/2 -91, (this.height-20)/2 +20, 110, 20, new TranslationTextComponent("gui.simpleradio.volumeSlider").appendString(": "), new StringTextComponent("%"), 0, 200, RadioItem.getVolume(stack), 1D, 1, true);
        addButton(volume);

        Button disconnect = new Button((this.width-70)/2 +57, (this.height-20)/2 +20, 70, 20, new TranslationTextComponent("gui.simpleradio.disconnectButton"), button -> {
            PacketHandler.INSTANCE.sendToServer(new PlayerDisconnectRadioPacket());
        });
        addButton(disconnect);

        Button connect = new Button((this.width-70)/2 +57, (this.height-20)/2 -5, 70, 20, new TranslationTextComponent("gui.simpleradio.connectButton"), button -> {
            PacketHandler.INSTANCE.sendToServer(new PlayerConnectRadioPacket((short) channel.getValueInt()));
        });
        addButton(connect);
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
    {
        renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void renderBackground(MatrixStack matrixStack)
    {
        if (minecraft.world != null)
        {
            fillGradient(matrixStack, 0, 0, this.width, this.height, -1072689136, -804253680);
            minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
            blit(matrixStack, (this.width-195)/2, (this.height-76)/2, 0, 0, 195, 76);
            drawCenteredString(matrixStack, this.font, new TranslationTextComponent("gui.simpleradio.title"), this.width/2, this.height/2 - 30, 4210752);
        }
    }

    public static void drawCenteredString(MatrixStack matrixStack, FontRenderer fontRenderer, ITextComponent font, int text, int x, int y) {
        IReorderingProcessor ireorderingprocessor = font.func_241878_f();
        fontRenderer.drawText(matrixStack, font, (float)(text - fontRenderer.func_243245_a(ireorderingprocessor) / 2), (float)x, y);
    }
}
