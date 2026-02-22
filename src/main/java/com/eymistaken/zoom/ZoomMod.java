package com.eymistaken.zoom;

import com.eymistaken.zoom.config.ZoomConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ZoomMod implements ClientModInitializer {
    public static final String MOD_ID = "eymistakens_zoom";
    
    public static KeyBinding zoomKeyBinding;
    public static boolean isZooming = false;
    
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static File configFile;
    public static ZoomConfig config;

    @Override
    public void onInitializeClient() {
        configFile = new File(FabricLoader.getInstance().getConfigDir().toFile(), MOD_ID + ".json");
        loadConfig();

        zoomKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.eymistakens_zoom.zoom", 
                InputUtil.Type.KEYSYM, 
                GLFW.GLFW_KEY_C, 
                new KeyBinding.Category(net.minecraft.util.Identifier.of(MOD_ID, "category.main"))
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            isZooming = zoomKeyBinding.isPressed();
        });
    }

    public static void loadConfig() {
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                config = GSON.fromJson(reader, ZoomConfig.class);
            } catch (IOException e) {
                config = new ZoomConfig();
            }
        } else {
            config = new ZoomConfig();
            saveConfig();
        }
    }

    public static void saveConfig() {
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
