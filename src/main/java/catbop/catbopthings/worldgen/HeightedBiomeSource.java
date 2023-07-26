package catbop.catbopthings.worldgen;

import catbop.catbopthings.registries.ModBiomeSources;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSource;
import net.minecraft.world.level.biome.Climate;

import java.util.List;
import java.util.stream.Stream;

public class HeightedBiomeSource extends BiomeSource {
    public static final Codec<HeightedBiomeSource> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("fallback").forGetter(heightedBiomeSource -> heightedBiomeSource.fallback),
                    Codec.pair(Codec.INT.fieldOf("y").codec(), BiomeSource.CODEC.fieldOf("source").codec())
                            .listOf().fieldOf("heighted").forGetter(heightedBiomeSource -> heightedBiomeSource.sources)
            ).apply(instance, HeightedBiomeSource::new)
    );

    private final BiomeSource fallback;
    private final List<Pair<Integer, BiomeSource>> sources;

    public HeightedBiomeSource(BiomeSource fallback, List<Pair<Integer, BiomeSource>> sources) {
        this.fallback = fallback;
        this.sources = sources;
    }

    @Override
    protected Codec<? extends BiomeSource> codec() {
        return ModBiomeSources.HEIGHTED.get();
    }

    @Override
    protected Stream<Holder<Biome>> collectPossibleBiomes() {
        return Stream.concat(fallback.possibleBiomes().stream(), sources.stream().flatMap(pair -> pair.getSecond().possibleBiomes().stream()));
    }

    @Override
    public Holder<Biome> getNoiseBiome(int pX, int pY, int pZ, Climate.Sampler pSampler) {
        int blockY = QuartPos.toBlock(pY);
        for (Pair<Integer, BiomeSource> pair : sources) {
            if (blockY >= pair.getFirst()) {
                return pair.getSecond().getNoiseBiome(pX, pY, pZ, pSampler);
            }
        }
        return fallback.getNoiseBiome(pX, pY, pZ, pSampler);
    }
}
