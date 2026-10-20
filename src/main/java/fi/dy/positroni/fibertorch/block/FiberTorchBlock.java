package fi.dy.positroni.fibertorch.block;

import fi.dy.positroni.fibertorch.Config;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class FiberTorchBlock extends TorchBlock {

    public FiberTorchBlock(ParticleOptions particle) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.NONE)
                .noCollission()
                .instabreak()
                .pushReaction(PushReaction.DESTROY)
                .lightLevel((t) -> Config.FIBER_TORCH_LIGHT_LEVEL),
                particle);
    }

}
