package fun.swip.vegans.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public class NoHitting {
    @Inject(
            method = "attack",
            at = @At("HEAD")
    )
    public void attack(Entity target, CallbackInfo ci) {
        Player player = (Player) (Object) this;
        if (!(player instanceof ServerPlayer serverPlayer)) { return; }
        if (!target.showVehicleHealth()) { return; }

        System.out.println(serverPlayer + " tried attacking " + target);
        serverPlayer.connection.disconnect(Component.literal("You are not allowed to hit stuff?"));
        ci.cancel();
    }
}
