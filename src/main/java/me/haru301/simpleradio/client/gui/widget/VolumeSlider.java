package me.haru301.simpleradio.client.gui.widget;

import me.haru301.simpleradio.SimpleRadio;
import me.haru301.simpleradio.item.RadioItem;
import me.haru301.simpleradio.network.PacketHandler;
import me.haru301.simpleradio.network.packet.VolumeChangePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

public class VolumeSlider extends ForgeSlider
{
    public VolumeSlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double currentValue, double stepSize, int precision, boolean drawString)
    {
        super(x, y, width, height, prefix, suffix, minValue, maxValue, currentValue, stepSize, precision, drawString);
    }

    @Override
    public void onClick(double mouseX, double mouseY)
    {
        super.onClick(mouseX, mouseY);
        apply();
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY)
    {
        super.onDrag(mouseX, mouseY, dragX, dragY);
        apply();
    }

    @Override
    public void onRelease(double mouseX, double mouseY)
    {
        super.onRelease(mouseX, mouseY);
        apply();
    }

    public void apply()
    {
        PacketHandler.INSTANCE.sendToServer(new VolumeChangePacket((short) this.getValueInt()));
    }
}
