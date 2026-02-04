package fun.swip.vegans;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import java.util.concurrent.CompletableFuture;

public class VegansRecipeProvider extends FabricRecipeProvider {
    public VegansRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider wrapperLookup, RecipeOutput recipeExporter) {
        return null;
    }

//    @Override
//    public void generate(RecipeExporter exporter) {
//        // Access registries like this now
//        RegistryWrapper.WrapperLookup registries = this.registries;
//
//        RegistryWrapper<Item> itemLookup =
//                registries.getOrThrow(RegistryKeys.ITEM);
//    }

    @Override
    public String getName() {
        return "";
    }
}
