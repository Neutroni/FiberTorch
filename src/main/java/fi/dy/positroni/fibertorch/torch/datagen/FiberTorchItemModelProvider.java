package fi.dy.positroni.fibertorch.torch.datagen;

import fi.dy.positroni.fibertorch.FiberTorch;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class FiberTorchItemModelProvider extends ItemModelProvider {

    public FiberTorchItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FiberTorch.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        final String torchPath = FiberTorch.FIBER_TORCH_BLOCK.getId().getPath();
        singleTexture(torchPath, mcLoc("item/generated"), "layer0", modLoc("block/" + torchPath));

        final String fiberPath = FiberTorch.PLANT_FIBER_ITEM.getId().getPath();
        singleTexture(fiberPath, mcLoc("item/generated"), "layer0", modLoc("item/" + fiberPath));
    }
}
