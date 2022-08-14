package me.haru301.simpleradio.init;

import me.haru301.simpleradio.SimpleRadio;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSounds
{
    public static final DeferredRegister<SoundEvent> REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, SimpleRadio.MOD_ID);

    public static final RegistryObject<SoundEvent> RADIO_ON = register("radio_on");
    public static final RegistryObject<SoundEvent> RADIO_OFF = register("radio_off");
    public static final RegistryObject<SoundEvent> RADIO_UNABLE = register("radio_unable");

    private static RegistryObject<SoundEvent> register(String key)
    {
        return REGISTER.register(key, () -> new SoundEvent(new ResourceLocation(SimpleRadio.MOD_ID, key)));
    }
}
