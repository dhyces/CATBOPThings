package catbop.catbopthings.mixin;

import catbop.catbopthings.ModMobEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract boolean hasEffect(MobEffect pEffect);

    @Inject(method = "isImmobile", at = @At("HEAD"), cancellable = true)
    private void catbopthings_stunImmobile(CallbackInfoReturnable<Boolean> cir) {
        if (hasEffect(ModMobEffects.STUN.get())) {
            cir.setReturnValue(true);
        }
    }
}
