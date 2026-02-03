package fun.swip.vegans;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class TreeScore {
    private static int globalTickCounter = 0;
    private static final int POINT_INCREMENT_INTERVAL = 200; // In ticks

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(minecraftServer ->  {
            globalTickCounter++;

            if (globalTickCounter >= POINT_INCREMENT_INTERVAL) {
                for (ServerPlayerEntity player : minecraftServer.getPlayerManager().getPlayerList()) {
                    int currentScore = player.getAttachedOrCreate(Vegans.TREE_SCORE);
                    player.setAttached(Vegans.TREE_SCORE, currentScore + 1);
                }
                System.out.println("The forest shares its strength! (+1 treescore to every online player)");
                globalTickCounter = 0;
            }
        });
    }
}
