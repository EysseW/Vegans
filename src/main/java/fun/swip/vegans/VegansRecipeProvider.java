package fun.swip.vegans;

import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.world.item.Items;

public class VegansRecipeProvider extends FabricRecipeProvider {
    public VegansRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);

                // Brown dye recipes
                shapeless(RecipeCategory.DECORATIONS, Items.BROWN_DYE, 2)
                        .requires(Items.GREEN_DYE)
                        .requires(Items.RED_DYE)
                        .unlockedBy(getHasName(Items.BROWN_DYE), has(Items.GREEN_DYE))
                        .save(output);

                // Brown dye recipes
                shapeless(RecipeCategory.BUILDING_BLOCKS, Items.WHITE_WOOL, 4)
                        .requires(Items.STRING, 4)
                        .unlockedBy(getHasName(Items.WHITE_WOOL), has(Items.WHITE_WOOL))
                        .save(output);

            }
        };
    }

    @Override
    public String getName() {
        return "VegansRecipeProvider";
    }
}