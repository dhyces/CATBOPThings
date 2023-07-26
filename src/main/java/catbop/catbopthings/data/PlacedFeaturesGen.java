package catbop.catbopthings.data;

import catbop.catbopthings.CatbopThings;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

import java.util.List;

public class PlacedFeaturesGen {
    public static final ResourceKey<PlacedFeature> BASALT_MOUND = key("basalt_mound");
    public static final ResourceKey<PlacedFeature> OBSIDIAN_MOUND = key("obsidian_mound");

    public static Holder<PlacedFeature> basaltMound;
    public static Holder<PlacedFeature> obsidianMound;

    static void run(BootstapContext<PlacedFeature> context) {
        basaltMound = context.register(BASALT_MOUND, new PlacedFeature(ConfiguredFeaturesGen.basaltMound, List.of(BiomeFilter.biome(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), CountPlacement.of(50))));
        obsidianMound = context.register(OBSIDIAN_MOUND, new PlacedFeature(ConfiguredFeaturesGen.obsidianMound, List.of(BiomeFilter.biome(), HeightmapPlacement.onHeightmap(Heightmap.Types.WORLD_SURFACE_WG), CountPlacement.of(50))));
    }

    private static ResourceKey<PlacedFeature> key(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, CatbopThings.id(id));
    }
}
