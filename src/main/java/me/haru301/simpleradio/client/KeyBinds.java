package me.haru301.simpleradio.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyBinds
{
    public static final KeyBinding PTT_KEY = new KeyBinding("key.simpleradio.ptt", GLFW.GLFW_KEY_V,"key.categories.simpleradio");
    public static void register()
    {
        ClientRegistry.registerKeyBinding(PTT_KEY);
    }
}
