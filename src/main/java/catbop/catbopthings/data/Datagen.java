package catbop.catbopthings.data;

import catbop.catbopthings.CatbopThings;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = CatbopThings.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Datagen {

    @SubscribeEvent
    static void datagen(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        RegistrySetBuilder builder = new RegistrySetBuilder()
                .add(Registries.CONFIGURED_FEATURE, ConfiguredFeaturesGen::run)
                .add(Registries.PLACED_FEATURE, PlacedFeaturesGen::run);

        generator.addProvider(event.includeServer(), new DatapackBuiltinEntriesProvider(packOutput, lookupProvider, builder, Set.of(CatbopThings.MODID)));
    }

    private static ResourceKey<PlacedFeature> placedFeature(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, CatbopThings.id(id));
    }
}
