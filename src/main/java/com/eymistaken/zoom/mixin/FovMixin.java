package com.eymistaken.zoom.mixin;

import com.eymistaken.zoom.ZoomMod;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class FovMixin {

    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    private void onGetFov(net.minecraft.client.render.Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Float> cir) {
        if (ZoomMod.config != null) {
            float lerpedZoom = net.minecraft.util.math.MathHelper.lerp(tickDelta, ZoomMod.lastZoomMultiplier, ZoomMod.currentZoomMultiplier);
            if (lerpedZoom > 1.0f) {
                float currentFov = cir.getReturnValue();
                cir.setReturnValue(currentFov / lerpedZoom);
            }
        }
    }
}
