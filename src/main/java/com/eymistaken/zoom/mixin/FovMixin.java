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
        if (ZoomMod.isZooming && ZoomMod.config != null) {
            float currentFov = cir.getReturnValue();
            cir.setReturnValue((float) (currentFov / ZoomMod.config.zoomMultiplier));
        }
    }
}
