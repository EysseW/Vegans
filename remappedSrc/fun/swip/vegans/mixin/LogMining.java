package fun.swip.vegans.mixin;

import fun.swip.vegans.Vegans;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class LogMining {
    @Inject(
            method = "playerWillDestroy",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onBlockBreak(Level world, BlockPos pos, BlockState state, Player player, CallbackInfoReturnable<BlockState> cir) {
        if (state.is(BlockTags.LOGS)) {

            Integer currentScore = player.getAttachedOrCreate(Vegans.TREE_SCORE);
            System.out.println(currentScore);
            if (currentScore > 0) {
                ExperienceOrb.award((ServerLevel) world, Vec3.atCenterOf(pos),5);
                player.setAttached(Vegans.TREE_SCORE, currentScore - 1);
            } else if (player instanceof ServerPlayer serverPlayer) {
                cir.setReturnValue(state);
                serverPlayer.connection.disconnect(Component.nullToEmpty("Deforestation is not the answer!"));
            } else {
                System.out.println("Error: mining entity isn't a serverPlayer");
            }
        }
    }
}
