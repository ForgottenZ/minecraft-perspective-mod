package com.example.minecraftperspectivemod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Shadow
    @Final
    public GameOptions options;

    @Shadow
    @Nullable
    public ClientPlayerEntity player;

    @Inject(method = "hasOutline", at = @At("HEAD"), cancellable = true)
    private void minecraftPerspectiveMod$allowOutlinesInAllModes(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (!(entity instanceof PlayerEntity)) {
            return;
        }

        if (this.player == null || this.player.isSpectator()) {
            return;
        }

        if (entity == this.player) {
            return;
        }

        if (this.options.spectatorOutlinesKey.isPressed()) {
            cir.setReturnValue(true);
        }
    }
}
