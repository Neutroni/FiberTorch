package fi.dy.positroni.fibertorch.torch.datagen;

import fi.dy.positroni.fibertorch.FiberTorch;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class FiberTorchBlockStateProvider extends BlockStateProvider {

    public FiberTorchBlockStateProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FiberTorch.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        final RegistryObject<Block> block = FiberTorch.FIBER_TORCH_BLOCK;
        final String blockPath = block.getId().getPath();
        final ResourceLocation texture = modLoc("block/" + blockPath);
        final ModelFile torch = models().torch(blockPath, texture).renderType("cutout");
        simpleBlock(block.get(), torch);

        final RegistryObject<Block> wallBlock = FiberTorch.WALL_FIBER_TORCH_BLOCK;
        final ModelFile wallTorch = models().torchWall("wall_" + blockPath, texture).renderType("cutout");
        getVariantBuilder(wallBlock.get()).forAllStates(
                state -> ConfiguredModel.builder()
                        .modelFile(wallTorch)
                        .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 90) % 360)
                        .build());
    }
}
