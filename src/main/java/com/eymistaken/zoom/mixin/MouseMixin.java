package com.eymistaken.zoom.mixin;

import com.eymistaken.zoom.ZoomMod;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(Mouse.class)
public class MouseMixin {

    @ModifyArgs(
        method = "updateMouse(D)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;changeLookDirection(DD)V")
    )
    private void modifyLookDirection(Args args) {
        if (ZoomMod.currentZoomMultiplier > 1.001f) {
            double x = args.get(0);
            double y = args.get(1);
            
            args.set(0, x / (double)ZoomMod.currentZoomMultiplier);
            args.set(1, y / (double)ZoomMod.currentZoomMultiplier);
        }
    }
}
