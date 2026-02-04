package fun.swip.vegans;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class ScoreCommand {
    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(Commands.literal("treescore")
                    .executes(ScoreCommand::execute)
            );
        });
    }
    private static int execute(CommandContext<CommandSourceStack> context) {
        try {
            // 1. Get the player from the source
            ServerPlayer player = context.getSource().getPlayerOrException();

            // 2. Fetch the score safely
            int score = player.getAttachedOrCreate(Vegans.TREE_SCORE);

            // 3. Print it to the chat
            context.getSource().sendSuccess(() ->
                            Component.literal("Current Tree Score: ")
                                    .append(Component.literal(String.valueOf(score)).withStyle(ChatFormatting.GREEN)),
                    false
            );

            return 1; // Success
        } catch (Exception e) {
            context.getSource().sendFailure(Component.literal("This command must be run by a player!"));
            return 0;
        }
    }
}
