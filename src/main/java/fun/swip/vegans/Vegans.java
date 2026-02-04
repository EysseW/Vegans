package fun.swip.vegans;

import net.fabricmc.api.ModInitializer;

public class Vegans implements ModInitializer {
    @Override
    public void onInitialize() {
        TreeScore.register();
        ScoreCommand.register();
        NoIgnition.register();
    }
}
