package catbop.catbopthings;

import catbop.catbopthings.data.CatbopMaterialProvider;
import catbop.catbopthings.data.CatbopModifierProvider;
import catbop.catbopthings.modifiers.CraftedModifier;
import catbop.catbopthings.modifiers.PrismiteModifier;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.ModifierManager;

@Mod(CatbopThings.MODID)
public class CatbopThings {
    public static final String MODID = "catbopthings";

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MODID, id);
    }

    public static ModifierId modifierId(String id) {
        return new ModifierId(MODID, id);
    }

    public CatbopThings() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModMobEffects.register(modBus);

        modBus.addListener(this::registerModifier);

        if (FMLLoader.getDist().isClient()) {
            new CatbopThingsClient(modBus, MinecraftForge.EVENT_BUS);
        }

        if (!FMLLoader.isProduction()) {
            modBus.addListener(this::datagen);
        }
    }

    private void registerModifier(ModifierManager.ModifierRegistrationEvent event) {
        event.registerStatic(CatbopThings.modifierId("prismite"), new PrismiteModifier());
        event.registerStatic(CatbopThings.modifierId("crafted"), new CraftedModifier());
    }

    private void datagen(final GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new CatbopMaterialProvider(generator));
        generator.addProvider(new CatbopModifierProvider(generator));
    }
}
