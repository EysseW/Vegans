package fun.swip.vegans;

import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack; // Fixed: Use CommandSourceStack
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

    private static int execute(CommandContext<CommandSourceStack> context) { // Fixed generic type
        try {
            // 1. Get the player from the source
            // getPlayerOrThrow() is perfect for Mojmap 1.21.x
            ServerPlayer player = context.getSource().getPlayer();

            // 2. Fetch the score safely from your Data Attachment
            int score = player.getAttachedOrCreate(TreeScore.TREE_SCORE);

            // 3. Print it to the chat
            // sendSuccess requires (Supplier<Component>, boolean)
            context.getSource().sendSuccess(() ->
                            Component.literal("Current Tree Score: ")
                                    .append(Component.literal(String.valueOf(score)).withStyle(ChatFormatting.GREEN)),
                    false
            );

            return 1; // Success
        } catch (Exception e) {
            // sendFailure is the correct way to handle errors in Mojmap commands
            context.getSource().sendFailure(Component.literal("This command must be run by a player!"));
            return 0;
        }
    }
}