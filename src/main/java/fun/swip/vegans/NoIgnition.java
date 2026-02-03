package fun.swip.vegans;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Box;

import java.util.List;

public class NoIgnition {
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            // 1. Get the item in the hand being used
            ItemStack stack = player.getStackInHand(hand);

            // 2. Check if it's one of your "illegal" items
            if (player instanceof ServerPlayerEntity serverPlayer) {
                if (stack.isOf(Items.FLINT_AND_STEEL) || stack.isOf(Items.LAVA_BUCKET) || stack.isOf(Items.FIRE_CHARGE)) {

                    // 3. Perform your radius check logic...
                    Box collideCheck = new Box(player.getBlockPos()).expand(4.0);

                    List<LivingEntity> entities = world.getEntitiesByClass(
                            LivingEntity.class,
                            collideCheck,
                            entity -> entity.isAlive() && entity != serverPlayer
                    );
                    // (Assuming you find an entity nearby)
                    for (LivingEntity victim : entities) {
                        // This is the "Cancel" button:
                        serverPlayer.networkHandler.disconnect(Text.literal("You are not allowed to ignite creatures (OF COURSE!!)"));
                        return ActionResult.FAIL;
                    }
                }
            }

            // Otherwise, let the game continue normally
            return ActionResult.PASS;
        });
    }
}
