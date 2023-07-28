package catbop.catbopthings;

import catbop.catbopthings.registries.ModMobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;

@Mod(CatbopThings.MODID)
public class CatbopThings {
    public static final String MODID = "catbopthings";

    public static ResourceLocation id(String id) {
        return new ResourceLocation(MODID, id);
    }

//    public static ModifierId modifierId(String id) {
//        return new ModifierId(MODID, id);
//    }

    public CatbopThings() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModMobEffects.REGISTRY.register(modBus);

//        modBus.addListener(this::registerModifier);

        if (FMLLoader.getDist().isClient()) {
            new CatbopThingsClient(modBus, MinecraftForge.EVENT_BUS);
        }
    }

//    private void registerModifier(ModifierManager.ModifierRegistrationEvent event) {
//        event.registerStatic(CatbopThings.modifierId("prismite"), new PrismiteModifier());
//        event.registerStatic(CatbopThings.modifierId("crafted"), new CraftedModifier());
//    }
}
