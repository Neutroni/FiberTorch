package fi.dy.positroni.fibertorch.torch.datagen;

import fi.dy.positroni.fibertorch.FiberTorch;
import java.util.Collections;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

public class FiberTorchLootTableSubProvider extends BlockLootSubProvider {

    public FiberTorchLootTableSubProvider() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return FiberTorch.BLOCKS.getEntries()
                .stream()
                .map(e -> e.get())
                .toList();
    }

    @Override
    protected void generate() {
        dropSelf(FiberTorch.FIBER_TORCH_BLOCK.get());
    }
}
