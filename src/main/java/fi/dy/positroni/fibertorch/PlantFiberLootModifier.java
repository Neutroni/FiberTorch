package fi.dy.positroni.fibertorch;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import java.util.function.Supplier;

public class PlantFiberLootModifier extends LootModifier {

    public static final Supplier<Codec<PlantFiberLootModifier>> CODEC = Suppliers.memoize(
            () -> RecordCodecBuilder.create(inst -> codecStart(inst).apply(inst, PlantFiberLootModifier::new))
    );

    public PlantFiberLootModifier(LootItemCondition[] condition) {
        super(condition);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        if (Math.random() * 100 < Config.plantFiberDropChance){
            generatedLoot.add(new ItemStack(FiberTorch.PLANT_FIBER_ITEM.get(), 1));
        }
        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

}
