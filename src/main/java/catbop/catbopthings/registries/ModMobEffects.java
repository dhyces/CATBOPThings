package catbop.catbopthings.registries;

import catbop.catbopthings.CatbopThings;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModMobEffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(Registries.MOB_EFFECT, CatbopThings.MODID);

    public static final RegistryObject<MobEffect> STUN = REGISTRY.register("stun", () -> new MobEffect(MobEffectCategory.NEUTRAL, 0xCECA31) {});
}
