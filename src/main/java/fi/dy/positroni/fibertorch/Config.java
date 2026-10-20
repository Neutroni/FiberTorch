package fi.dy.positroni.fibertorch;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.event.config.ModConfigEvent;

public class Config {

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.IntValue PLANT_FIBER_DROP_CHANCE = BUILDER
            .comment("Plant fiber drop chance")
            .defineInRange("plantFiberDropChance", 100, 0, 100);

    //There doesn't appear to be a way to configure light level from config
    public static final int FIBER_TORCH_LIGHT_LEVEL = 7;

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static int plantFiberDropChance;

    static void onLoad(final ModConfigEvent event) {
        plantFiberDropChance = PLANT_FIBER_DROP_CHANCE.get();
    }
}
