package me.haru301.simpleradio.item;

import me.haru301.simpleradio.client.gui.Gui;
import me.haru301.simpleradio.init.ModItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.List;

public class RadioItem extends Item
{
    public static final String CHANNEL_NBT = "channel";
    public static final String VOLUME_NBT = "volume";
    public static final String RECEIVE_ONLY_NBT = "receiver";

    public RadioItem(Properties properties)
    {
        super(properties);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand)
    {
        ItemStack stack = player.getHeldItemMainhand();
        Minecraft.getInstance().displayGuiScreen(new Gui(new TranslationTextComponent("gui.walkietalkie.settings"), stack));
        return super.onItemRightClick(world, player, hand);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("text.simpleradio.channel").appendString(": CH."+String.valueOf(getChannel(stack))));
        tooltip.add(new TranslationTextComponent("text.simpleradio.volume").appendString(": "+String.valueOf(getVolume(stack))+"%"));
    }

    // check item stack is walkie talkie item
    public static boolean isValid(ItemStack stack)
    {
        return stack.getItem().equals(ModItems.RADIO.get());
    }

    public static void setChannel(ItemStack stack, short channel)
    {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putShort(CHANNEL_NBT, channel);
    }

    public static void setVolume(ItemStack stack, short volume)
    {
        CompoundNBT nbt = stack.getOrCreateTag();
        nbt.putShort(VOLUME_NBT, volume);
    }

    public static short getChannel(ItemStack stack)
    {
        CompoundNBT nbt = stack.getOrCreateTag();
        if(nbt.contains(CHANNEL_NBT)) return nbt.getShort(CHANNEL_NBT);
        else return 1; //default
    }

    public static short getVolume(ItemStack stack)
    {
        CompoundNBT nbt = stack.getOrCreateTag();
        if(nbt.contains(VOLUME_NBT)) return nbt.getShort(VOLUME_NBT);
        else return 100; //default
    }
}
