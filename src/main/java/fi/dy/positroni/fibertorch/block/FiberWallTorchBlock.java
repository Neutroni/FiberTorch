package fi.dy.positroni.fibertorch.block;

import fi.dy.positroni.fibertorch.Config;
import static fi.dy.positroni.fibertorch.FiberTorch.FIBER_TORCH_BLOCK;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class FiberWallTorchBlock extends WallTorchBlock {

    public FiberWallTorchBlock(ParticleOptions particleOptions) {
        super(BlockBehaviour.Properties.of()
                .mapColor(MapColor.NONE)
                .noCollission()
                .instabreak()
                .pushReaction(PushReaction.DESTROY)
                .lightLevel((t) -> Config.FIBER_TORCH_LIGHT_LEVEL)
                .lootFrom(() -> FIBER_TORCH_BLOCK.get()),
                particleOptions);
    }

}
