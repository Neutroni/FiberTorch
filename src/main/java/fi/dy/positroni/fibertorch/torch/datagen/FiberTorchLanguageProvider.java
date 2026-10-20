package fi.dy.positroni.fibertorch.torch.datagen;

import fi.dy.positroni.fibertorch.FiberTorch;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

public class FiberTorchLanguageProvider extends LanguageProvider {

    public FiberTorchLanguageProvider(PackOutput output, String modId, String locale ) {
        super(output, modId, locale);
    }

    @Override
    protected void addTranslations() {
        this.addBlock(FiberTorch.FIBER_TORCH_BLOCK, "Fiber torch");
        this.addItem(FiberTorch.PLANT_FIBER_ITEM, "Plant fiber");
    }
    
}
