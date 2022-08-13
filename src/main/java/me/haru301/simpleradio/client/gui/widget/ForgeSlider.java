package me.haru301.simpleradio.client.gui.widget;

/*
 * Minecraft Forge - Forge Development LLC
 * SPDX-License-Identifier: LGPL-2.1-only
 */

import net.minecraft.client.gui.widget.AbstractSlider;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.lwjgl.glfw.GLFW;

import java.text.DecimalFormat;

/**
 * Slider widget implementation which allows inputting values in a certain range with optional step size.
 */
public class ForgeSlider extends AbstractSlider
{
    protected ITextComponent prefix;
    protected ITextComponent suffix;

    protected double minValue;
    protected double maxValue;

    /** Allows input of discontinuous values with a certain step */
    protected double stepSize;

    protected boolean drawString;

    private final DecimalFormat format;

    /**
     * @param x x position of upper left corner
     * @param y y position of upper left corner
     * @param width Width of the widget
     * @param height Height of the widget
     * @param prefix {@link ITextComponent} displayed before the value string
     * @param suffix {@link ITextComponent} displayed after the value string
     * @param minValue Minimum (left) value of slider
     * @param maxValue Maximum (right) value of slider
     * @param currentValue Starting value when widget is first displayed
     * @param stepSize Size of step used. Precision will automatically be calculated based on this value if this value is not 0.
     * @param precision Only used when {@code stepSize} is 0. Limited to a maximum of 4 (inclusive).
     * @param drawString Should text be displayed on the widget
     */
    public ForgeSlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double currentValue, double stepSize, int precision, boolean drawString)
    {
        super(x, y, width, height, new TranslationTextComponent(""), 0D);
        this.prefix = prefix;
        this.suffix = suffix;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.stepSize = Math.abs(stepSize);
        this.sliderValue = this.snapToNearest((currentValue - minValue) / (maxValue - minValue));
        this.drawString = drawString;

        if (stepSize == 0D)
        {
            precision = Math.min(precision, 4);

            StringBuilder builder = new StringBuilder("0");

            if (precision > 0)
                builder.append('.');

            while (precision-- > 0)
                builder.append('0');

            this.format = new DecimalFormat(builder.toString());
        }
        else if (MathHelper.epsilonEquals(this.stepSize, Math.floor(this.stepSize)))
        {
            this.format = new DecimalFormat("0");
        }
        else
        {
            this.format = new DecimalFormat(Double.toString(this.stepSize).replaceAll("\\d", "0"));
        }

        this.func_230979_b_();
    }

    /**
     * Overload with {@code stepSize} set to 1, useful for sliders with whole number values.
     */
    public ForgeSlider(int x, int y, int width, int height, ITextComponent prefix, ITextComponent suffix, double minValue, double maxValue, double currentValue, boolean drawString)
    {
        this(x, y, width, height, prefix, suffix, minValue, maxValue, currentValue, 1D, 0, drawString);
    }

    /**
     * @return Current slider value as a double
     */
    public double getValue()
    {
        return this.sliderValue * (maxValue - minValue) + minValue;
    }

    /**
     * @return Current slider value as an long
     */
    public long getValueLong()
    {
        return Math.round(this.getValue());
    }

    /**
     * @return Current slider value as an int
     */
    public int getValueInt()
    {
        return (int) this.getValueLong();
    }

    /**
     * @param value The new slider value
     */
    public void setValue(double value)
    {
        this.sliderValue = this.snapToNearest((value - this.minValue) / (this.maxValue - this.minValue));
        this.func_230979_b_();
    }

    public String getValueString()
    {
        return this.format.format(this.getValue());
    }

    @Override
    public void onClick(double mouseX, double mouseY)
    {
        this.setValueFromMouse(mouseX);
    }

    @Override
    protected void onDrag(double mouseX, double mouseY, double dragX, double dragY)
    {
        super.onDrag(mouseX, mouseY, dragX, dragY);
        this.setValueFromMouse(mouseX);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers)
    {
        boolean flag = keyCode == GLFW.GLFW_KEY_LEFT;
        if (flag || keyCode == GLFW.GLFW_KEY_RIGHT)
        {
            if (this.minValue > this.maxValue)
                flag = !flag;
            float f = flag ? -1F : 1F;
            if (stepSize <= 0D)
                this.setSliderValue(this.sliderValue + (f / (this.width - 8)));
            else
                this.setValue(this.getValue() + f * this.stepSize);
        }

        return false;
    }

    private void setValueFromMouse(double mouseX)
    {
        this.setSliderValue((mouseX - (this.x + 4)) / (this.width - 8));
    }

    /**
     * @param value Percentage of slider range
     */
    private void setSliderValue(double value)
    {
        double oldValue = this.sliderValue;
        this.sliderValue = this.snapToNearest(value);
        if (!MathHelper.epsilonEquals(oldValue, this.sliderValue))
            this.func_230972_a_();

        this.func_230979_b_();
    }

    /**
     * Snaps the value, so that the displayed value is the nearest multiple of {@code stepSize}.
     * If {@code stepSize} is 0, no snapping occurs.
     */
    private double snapToNearest(double value)
    {
        if(stepSize <= 0D)
            return MathHelper.clamp(value, 0D, 1D);

        value = MathHelper.lerp(MathHelper.clamp(value, 0D, 1D), this.minValue, this.maxValue);

        value = (stepSize * Math.round(value / stepSize));

        if (this.minValue > this.maxValue)
        {
            value = MathHelper.clamp(value, this.maxValue, this.minValue);
        }
        else
        {
            value = MathHelper.clamp(value, this.minValue, this.maxValue);
        }

        return map(value, this.minValue, this.maxValue, 0D, 1D);
    }

    //updateMessage()
    @Override
    protected void func_230979_b_()
    {
        if (this.drawString)
        {
            this.setMessage(new TranslationTextComponent("").appendSibling(prefix).appendString(this.getValueString()).appendSibling(suffix));
        }
        else
        {
            this.setMessage(new TranslationTextComponent(""));
        }
    }

    //applyValue()
    @Override
    protected void func_230972_a_() {}


    //MTH UTIL
    private static double map(double p_144915_, double p_144916_, double p_144917_, double p_144918_, double p_144919_) {
        return MathHelper.lerp(inverseLerp(p_144915_, p_144916_, p_144917_), p_144918_, p_144919_);
    }
    private static double inverseLerp(double p_14113_, double p_14114_, double p_14115_) {
        return (p_14113_ - p_14114_) / (p_14115_ - p_14114_);
    }
}