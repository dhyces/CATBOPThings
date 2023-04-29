package catbop.catbopthings;

import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {
    private static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registry.MOB_EFFECT_REGISTRY, CatbopThings.MODID);

    static void register(IEventBus modBus) {
        EFFECTS.register(modBus);
    }

    public static final RegistryObject<MobEffect> STUN = EFFECTS.register("stun", () -> new MobEffect(MobEffectCategory.NEUTRAL, 0xCECA31) {});
}
