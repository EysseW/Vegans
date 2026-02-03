package fun.swip.vegans.mixin;

import fun.swip.vegans.Vegans;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class LogMining {
    @Inject(
            method = "onBreak",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onBlockBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfoReturnable<BlockState> cir) {
        if (state.isIn(BlockTags.LOGS)) {

            Integer currentScore = player.getAttachedOrCreate(Vegans.TREE_SCORE);
            System.out.println(currentScore);
            if (currentScore > 0) {
                ExperienceOrbEntity.spawn((ServerWorld) world, Vec3d.ofCenter(pos),5);
                player.setAttached(Vegans.TREE_SCORE, currentScore - 1);
            } else if (player instanceof ServerPlayerEntity serverPlayer) {
                cir.setReturnValue(state);
                serverPlayer.networkHandler.disconnect(Text.of("Deforestation is not the answer!"));
            } else {
                System.out.println("Error: mining entity isn't a serverPlayer");
            }
        }
    }
}
