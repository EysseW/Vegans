package fun.swip.vegans.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class NoHitting {
    @Inject(
            method = "attack",
            at = @At("HEAD")
    )
    public void attack(Entity target, CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (!(player instanceof ServerPlayerEntity serverPlayer)) { return; }
        if (!target.isLiving()) { return; }

        System.out.println(serverPlayer + " tried attacking " + target);
        serverPlayer.networkHandler.disconnect(Text.literal("You are not allowed to hit stuff?"));
        ci.cancel();
    }
}
