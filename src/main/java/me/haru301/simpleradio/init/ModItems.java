package me.haru301.simpleradio.init;

import me.haru301.simpleradio.SimpleRadio;
import me.haru301.simpleradio.item.RadioItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems
{
    public static final DeferredRegister<Item> REGISTER = DeferredRegister.create(ForgeRegistries.ITEMS, SimpleRadio.MOD_ID);

    public static final RegistryObject<Item> RADIO = REGISTER.register( "radio", () -> new RadioItem(new Item.Properties().group(SimpleRadio.GROUP).maxStackSize(1)));
}
