package fun.swip.vegans;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

import static net.fabricmc.fabric.impl.networking.NetworkingImpl.MOD_ID;

public class TreeScore {
    private static int globalTickCounter = 0;
    private static final int POINT_INCREMENT_INTERVAL = 200; // In ticks

    // This creates the "Sticky Note" definition
    public static final AttachmentType<Integer> TREE_SCORE = AttachmentRegistry.create(
            Identifier.fromNamespaceAndPath(MOD_ID, "tree_score"),
            builder -> builder
                    .initializer(() -> 10)
                    .syncWith(
                            ByteBufCodecs.VAR_INT,
                            AttachmentSyncPredicate.all()
                    )
    );

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(minecraftServer ->  {
            globalTickCounter++;

            if (globalTickCounter >= POINT_INCREMENT_INTERVAL) {
                for (ServerPlayer player : minecraftServer.getPlayerList().getPlayers()) {
                    int currentScore = player.getAttachedOrCreate(TreeScore.TREE_SCORE);
                    player.setAttached(TREE_SCORE, currentScore + 1);
                }
                System.out.println("The forest shares its strength! (+1 treescore to every online player)");
                globalTickCounter = 0;
            }
        });
    }
}
