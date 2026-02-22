package com.eymistaken.zoom.integration;

import com.eymistaken.zoom.ZoomMod;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.text.Text;

public class ZoomModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Text.literal("Eymistaken's Zoom Settings"));

            builder.setSavingRunnable(ZoomMod::saveConfig);

            ConfigCategory general = builder.getOrCreateCategory(Text.literal("General"));
            ConfigEntryBuilder entryBuilder = builder.entryBuilder();

            general.addEntry(entryBuilder.startDoubleField(Text.literal("Zoom Multiplier"), ZoomMod.config.zoomMultiplier)
                    .setDefaultValue(4.0)
                    .setTooltip(Text.literal("How much the camera zooms in (e.g., 4.0 = 4x zoom)"))
                    .setSaveConsumer(newValue -> ZoomMod.config.zoomMultiplier = newValue)
                    .build());

            ConfigCategory hudSettings = builder.getOrCreateCategory(Text.literal("HUD Settings"));

            hudSettings.addEntry(entryBuilder.startEnumSelector(Text.literal("Position"), com.eymistaken.simplecps.SimpleCPSConfig.Position.class, ZoomMod.config.hudPosition)
                    .setDefaultValue(com.eymistaken.simplecps.SimpleCPSConfig.Position.BOTTOM_LEFT)
                    .setSaveConsumer(newValue -> ZoomMod.config.hudPosition = newValue)
                    .build());

            hudSettings.addEntry(entryBuilder.startIntField(Text.literal("X Offset"), ZoomMod.config.hudXOffset)
                    .setDefaultValue(0)
                    .setSaveConsumer(newValue -> ZoomMod.config.hudXOffset = newValue)
                    .build());

            hudSettings.addEntry(entryBuilder.startIntField(Text.literal("Y Offset"), ZoomMod.config.hudYOffset)
                    .setDefaultValue(0)
                    .setSaveConsumer(newValue -> ZoomMod.config.hudYOffset = newValue)
                    .build());

            hudSettings.addEntry(entryBuilder.startIntSlider(Text.literal("Scale (%)"), ZoomMod.config.hudScale, 50, 200)
                    .setDefaultValue(100)
                    .setSaveConsumer(newValue -> ZoomMod.config.hudScale = newValue)
                    .build());

            return builder.build();
        };
    }
}
