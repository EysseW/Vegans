package fun.swip.vegans;

import com.mojang.datafixers.optics.Lens;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import org.apache.logging.log4j.core.tools.picocli.CommandLine;

import java.util.List;

public class NoIgnition {
    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            // 1. Get the item in the hand being used
            ItemStack stack = player.getItemInHand(hand);

            // 2. Check if it's one of your "illegal" items
            if (player instanceof ServerPlayer serverPlayer) {
                if (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.LAVA_BUCKET) || stack.is(Items.FIRE_CHARGE)) {

                    // 3. Perform your radius check logic...
                    AABB collideCheck = new AABB(player.blockPosition()).inflate(4.0);

                    List<LivingEntity> entities = world.getEntitiesOfClass(
                            LivingEntity.class,
                            collideCheck,
                            entity -> entity.isAlive() && entity != serverPlayer
                    );
                    // (Assuming you find an entity nearby)
                    for (LivingEntity victim : entities) {
                        // This is the "Cancel" button:
                        serverPlayer.connection.disconnect(Component.literal("You are not allowed to ignite creatures (OF COURSE!!)"));
                        return InteractionResult.FAIL;
                    }
                }
            }

            return InteractionResult.PASS;
        });
    }
}
