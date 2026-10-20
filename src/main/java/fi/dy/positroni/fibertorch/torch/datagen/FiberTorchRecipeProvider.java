package fi.dy.positroni.fibertorch.torch.datagen;

import fi.dy.positroni.fibertorch.FiberTorch;
import java.util.function.Consumer;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;

public class FiberTorchRecipeProvider extends RecipeProvider {

    public FiberTorchRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> output) {
        Item fiber = FiberTorch.PLANT_FIBER_ITEM.get();
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, FiberTorch.FIBER_TORCH_ITEM.get(), 1)
                .pattern("F")
                .pattern("S")
                .define('F', fiber)
                .define('S', Items.STICK)
                .unlockedBy("has_plant_fiber", has(fiber))
                .save(output);
    }
}
