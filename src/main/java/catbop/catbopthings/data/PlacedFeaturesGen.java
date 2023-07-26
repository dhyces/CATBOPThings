package catbop.catbopthings.data;

import catbop.catbopthings.CatbopThings;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.ConstantHeight;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class PlacedFeaturesGen {
    public static final ResourceKey<PlacedFeature> BASALT_MOUND = key("basalt_mound");
    public static final ResourceKey<PlacedFeature> OBSIDIAN_MOUND = key("obsidian_mound");

    public static Holder<PlacedFeature> basaltMound;
    public static Holder<PlacedFeature> obsidianMound;

    static void run(BootstapContext<PlacedFeature> context) {
        basaltMound = context.register(BASALT_MOUND, new PlacedFeature(ConfiguredFeaturesGen.basaltMound, List.of(CountPlacement.of(50), HeightRangePlacement.of(ConstantHeight.of(VerticalAnchor.absolute(128))), RandomOffsetPlacement.horizontal(UniformInt.of(0, 8)), BiomeFilter.biome())));
        obsidianMound = context.register(OBSIDIAN_MOUND, new PlacedFeature(ConfiguredFeaturesGen.obsidianMound, List.of(CountPlacement.of(50), HeightRangePlacement.of(ConstantHeight.of(VerticalAnchor.absolute(128))), RandomOffsetPlacement.horizontal(UniformInt.of(0, 8)), BiomeFilter.biome())));
    }

    private static ResourceKey<PlacedFeature> key(String id) {
        return ResourceKey.create(Registries.PLACED_FEATURE, CatbopThings.id(id));
    }
}
