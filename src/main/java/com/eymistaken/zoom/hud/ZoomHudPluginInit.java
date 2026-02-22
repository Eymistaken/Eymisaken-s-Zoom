package com.eymistaken.zoom.hud;

import com.eymistaken.simplecps.HudModuleManager;
import com.eymistaken.simplecps.api.EymistakenHudPlugin;

public class ZoomHudPluginInit implements EymistakenHudPlugin {
    @Override
    public void registerHudModules(HudModuleManager manager) {
        manager.registerModule(new ZoomHudModule());
    }
}
