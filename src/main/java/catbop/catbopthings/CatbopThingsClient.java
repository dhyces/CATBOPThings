package catbop.catbopthings;

import catbop.catbopthings.registries.ModMobEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.joml.Matrix4f;

public final class CatbopThingsClient {
    public CatbopThingsClient(IEventBus modBus, IEventBus forgeBus) {
        forgeBus.addListener(this::keyPress);
    }

    private void keyPress(final MovementInputUpdateEvent event) {
        if (event.getEntity().hasEffect(ModMobEffects.STUN.get())) {
            event.getInput().down = false;
            event.getInput().up = false;
            event.getInput().left = false;
            event.getInput().right = false;
            event.getInput().jumping = false;
            event.getInput().shiftKeyDown = false;
            event.getInput().forwardImpulse = 0;
            event.getInput().leftImpulse = 0;
        }
    }
}
