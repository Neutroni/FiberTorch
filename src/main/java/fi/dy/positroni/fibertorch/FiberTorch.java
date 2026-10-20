package fi.dy.positroni.fibertorch;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import fi.dy.positroni.fibertorch.block.FiberTorchBlock;
import fi.dy.positroni.fibertorch.block.FiberWallTorchBlock;
import fi.dy.positroni.fibertorch.particle.FiberTorchFlameParticleProvider;
import fi.dy.positroni.fibertorch.torch.datagen.FiberTorchItemModelProvider;
import fi.dy.positroni.fibertorch.torch.datagen.FiberTorchRecipeProvider;
import fi.dy.positroni.fibertorch.torch.datagen.FiberTorchLootTableSubProvider;
import fi.dy.positroni.fibertorch.torch.datagen.FiberTorchBlockStateProvider;
import fi.dy.positroni.fibertorch.torch.datagen.FiberTorchGlobalLootModifierProvider;
import fi.dy.positroni.fibertorch.torch.datagen.FiberTorchLanguageProvider;
import fi.dy.positroni.fibertorch.torch.datagen.FiberTorchParticleDescriptionProvider;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.Direction;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableProvider.SubProviderEntry;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

@Mod(FiberTorch.MOD_ID)
public class FiberTorch {
    
    public static final String MOD_ID = "fibertorch";
    public static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Registers
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, MOD_ID);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(
            ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID
    );

    // Particle
    private static final SimpleParticleType flameParticle = new SimpleParticleType(false);
    public static final RegistryObject<SimpleParticleType> FIBER_TORCH_PARTICLE = PARTICLES.register(
            "fiber_torch_flame", () -> flameParticle
    );

    // Fiber torch
    public static final RegistryObject<Block> FIBER_TORCH_BLOCK = BLOCKS.register(
            "fiber_torch", () -> new FiberTorchBlock(flameParticle)
    );
    public static final RegistryObject<Block> WALL_FIBER_TORCH_BLOCK = BLOCKS.register(
            "wall_fiber_torch", () -> new FiberWallTorchBlock(flameParticle)
    );
    public static final RegistryObject<Item> FIBER_TORCH_ITEM = ITEMS.register(
            FIBER_TORCH_BLOCK.getId().getPath(),
            () -> new StandingAndWallBlockItem(
                    FIBER_TORCH_BLOCK.get(),
                    WALL_FIBER_TORCH_BLOCK.get(),
                    new Item.Properties(),
                    Direction.DOWN
            )
    );

    // Plant fiber
    public static final RegistryObject<Item> PLANT_FIBER_ITEM = ITEMS.register(
            "plant_fiber",
            () -> new Item(new Item.Properties())
    );
    public static final RegistryObject<Codec<PlantFiberLootModifier>> GRASS_LOOT_MODIFIER_CODEC = GLM.register(
            "grass_break", PlantFiberLootModifier.CODEC
    );
    
    public FiberTorch(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the Deferred Registers to the mod event bus
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        PARTICLES.register(modEventBus);
        GLM.register(modEventBus);
        
        modEventBus.addListener(Config::onLoad);
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::gatherData);
        modEventBus.addListener(this::registerParticleFactories);
        
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(FIBER_TORCH_ITEM);
        }
        if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(PLANT_FIBER_ITEM);
        }
    }
    
    protected void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(FIBER_TORCH_PARTICLE.get(), FiberTorchFlameParticleProvider::new);
    }
    
    public void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        
        generator.addProvider(
                event.includeServer(),
                new FiberTorchRecipeProvider(output)
        );
        generator.addProvider(
                event.includeServer(),
                new LootTableProvider(output, Collections.emptySet(),
                        List.of(new SubProviderEntry(FiberTorchLootTableSubProvider::new, LootContextParamSets.BLOCK))
                )
        );
        generator.addProvider(event.includeClient(),
                new FiberTorchBlockStateProvider(output, existingFileHelper)
        );
        generator.addProvider(
                event.includeClient(),
                new FiberTorchItemModelProvider(output, existingFileHelper)
        );
        generator.addProvider(
                event.includeClient(),
                new FiberTorchLanguageProvider(output, MOD_ID, "en_us")
        );
        generator.addProvider(
                event.includeServer(),
                new FiberTorchGlobalLootModifierProvider(output, MOD_ID)
        );
        generator.addProvider(
                event.includeClient(),
                new FiberTorchParticleDescriptionProvider(output, existingFileHelper)
        );
    }
}
