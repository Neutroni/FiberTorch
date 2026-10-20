package fi.dy.positroni.fibertorch.torch.datagen;

import fi.dy.positroni.fibertorch.PlantFiberLootModifier;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class FiberTorchGlobalLootModifierProvider extends GlobalLootModifierProvider {

    public FiberTorchGlobalLootModifierProvider(PackOutput output, String modid) {
        super(output, modid);
    }

    @Override
    protected void start() {
        this.add("plant_fiber_from_grass", new PlantFiberLootModifier(
                new LootItemCondition[]{
                    MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.FLINT)).build(),
                    new AnyOfCondition.Builder(new LootItemCondition.Builder[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS),
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS)
            }).build(),}
        ));
    }

}
