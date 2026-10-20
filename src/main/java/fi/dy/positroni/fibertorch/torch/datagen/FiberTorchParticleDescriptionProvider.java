package fi.dy.positroni.fibertorch.torch.datagen;

import fi.dy.positroni.fibertorch.FiberTorch;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ParticleDescriptionProvider;
import net.minecraftforge.registries.RegistryObject;

public class FiberTorchParticleDescriptionProvider extends ParticleDescriptionProvider {

    public FiberTorchParticleDescriptionProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, existingFileHelper);
    }

    @Override
    protected void addDescriptions() {
        final RegistryObject<SimpleParticleType> particle = FiberTorch.FIBER_TORCH_PARTICLE;
        final String particlePath = particle.getId().getPath();

        sprite(particle.get(), ResourceLocation.fromNamespaceAndPath(FiberTorch.MOD_ID, particlePath));
    }

}
