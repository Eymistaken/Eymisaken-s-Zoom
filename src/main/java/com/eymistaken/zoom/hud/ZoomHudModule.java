package com.eymistaken.zoom.hud;

import com.eymistaken.simplecps.SimpleCPSConfig;
import com.eymistaken.simplecps.api.HudModule;
import com.eymistaken.zoom.ZoomMod;
import net.minecraft.client.gui.DrawContext;

public class ZoomHudModule extends HudModule {

    @Override
    public String getName() {
        return "Eymistaken's Zoom";
    }

    @Override
    public SimpleCPSConfig.Position getPositionType() {
        return ZoomMod.config != null ? ZoomMod.config.hudPosition : SimpleCPSConfig.Position.BOTTOM_LEFT;
    }

    @Override
    public int getXOffset() {
        return ZoomMod.config != null ? ZoomMod.config.hudXOffset : 0;
    }

    @Override
    public int getYOffset() {
        return ZoomMod.config != null ? ZoomMod.config.hudYOffset : 0;
    }

    @Override
    public boolean isEnabled() {
        return ZoomMod.isZooming;
    }

    @Override
    public int getWidth() {
        if (!isEnabled() && !isEditor()) return 0;
        float scale = getScale() / 100f;
        return (int) (this.client.textRenderer.getWidth(getText()) * scale);
    }

    @Override
    public int getHeight() {
        if (!isEnabled() && !isEditor()) return 0;
        float scale = getScale() / 100f;
        return (int) (this.client.textRenderer.fontHeight * scale);
    }
    
    @Override
    public int getScale() {
        return ZoomMod.config != null ? ZoomMod.config.hudScale : 100;
    }

    private boolean isEditor() {
        return this.client.currentScreen instanceof com.eymistaken.simplecps.gui.HudEditorScreen;
    }

    private String getText() {
        if (!isEnabled() && isEditor()) {
            return "Zoom: 4.0X"; // Dummy display for editor
        }
        double multi = ZoomMod.config != null ? ZoomMod.config.zoomMultiplier : 4.0;
        return String.format("Zoom: %.1fX", multi); 
    }

    @Override
    public void render(DrawContext context, float tickDelta) {
        if (isEnabled() || isEditor()) {
            float scale = getScale() / 100f;
            context.getMatrices().pushMatrix();
            context.getMatrices().translate((float) this.x, (float) this.y);
            context.getMatrices().scale(scale, scale);
            
            context.drawTextWithShadow(this.client.textRenderer, getText(), 0, 0, 0xFFFFFFFF);
            
            context.getMatrices().popMatrix();
        }
    }

    // --- HUD Editor Integration Methods ---
    
    @Override
    public void setPositionType(SimpleCPSConfig.Position pos) {
        if(ZoomMod.config != null) {
            ZoomMod.config.hudPosition = pos;
            ZoomMod.saveConfig();
        }
    }

    @Override
    public void setXOffset(int x) {
        if(ZoomMod.config != null) {
            ZoomMod.config.hudXOffset = x;
            ZoomMod.saveConfig();
        }
    }

    @Override
    public void setYOffset(int y) {
        if(ZoomMod.config != null) {
            ZoomMod.config.hudYOffset = y;
            ZoomMod.saveConfig();
        }
    }

    @Override
    public void setScale(int scale) {
        if(ZoomMod.config != null) {
            ZoomMod.config.hudScale = scale;
            ZoomMod.saveConfig();
        }
    }
    
    // Note: getScale() is already mapped locally in this file returning float, 
    // the API might require an int version for the settings UI slider depending on implementation.
    // If we need the HUD getter itself it's below.

    @Override
    public void resetToDefaults() {
        if(ZoomMod.config != null) {
            ZoomMod.config.hudPosition = SimpleCPSConfig.Position.BOTTOM_LEFT;
            ZoomMod.config.hudXOffset = 0;
            ZoomMod.config.hudYOffset = 0;
            ZoomMod.config.hudScale = 100;
            ZoomMod.saveConfig();
        }
    }
}
