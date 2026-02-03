package fun.swip.vegans;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.permission.Permissions;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class ScoreCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("treescore")
                    .executes(ScoreCommand::execute)
            );
        });
    }
    private static int execute(CommandContext<ServerCommandSource> context) {
        try {
            // 1. Get the player from the source
            ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

            // 2. Fetch the score safely
            int score = player.getAttachedOrCreate(Vegans.TREE_SCORE);

            // 3. Print it to the chat
            context.getSource().sendFeedback(() ->
                            Text.literal("Current Tree Score: ")
                                    .append(Text.literal(String.valueOf(score)).formatted(Formatting.GREEN)),
                    false
            );

            return 1; // Success
        } catch (Exception e) {
            context.getSource().sendError(Text.literal("This command must be run by a player!"));
            return 0;
        }
    }
}
