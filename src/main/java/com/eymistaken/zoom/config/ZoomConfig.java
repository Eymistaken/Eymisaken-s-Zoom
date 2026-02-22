package com.eymistaken.zoom.config;

import com.eymistaken.simplecps.SimpleCPSConfig;

public class ZoomConfig {
    public double zoomMultiplier = 4.0;
    
    // HUD Settings
    public SimpleCPSConfig.Position hudPosition = SimpleCPSConfig.Position.BOTTOM_LEFT;
    public int hudXOffset = 0;
    public int hudYOffset = 0;
    public int hudScale = 100;
}
