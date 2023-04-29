package catbop.catbopthings;

import net.minecraftforge.client.event.MovementInputUpdateEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public final class CatbopThingsClient {
    public CatbopThingsClient(IEventBus modBus, IEventBus forgeBus) {
        forgeBus.addListener(this::keyPress);
    }

    private void keyPress(final MovementInputUpdateEvent event) {
        if (event.getPlayer().hasEffect(ModMobEffects.STUN.get())) {
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
