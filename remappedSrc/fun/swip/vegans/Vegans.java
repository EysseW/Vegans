package fun.swip.vegans;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.resources.Identifier;

import static net.fabricmc.fabric.impl.networking.NetworkingImpl.MOD_ID;

public class Vegans implements ModInitializer {
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


    @Override
    public void onInitialize() {
        TreeScore.register();
        ScoreCommand.register();
        NoIgnition.register();
    }
}
