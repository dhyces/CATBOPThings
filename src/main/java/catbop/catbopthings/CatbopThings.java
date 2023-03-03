package catbop.catbopthings;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.ModifierManager;

@Mod(CatbopThings.MODID)
public class CatbopThings {
    public static final String MODID = "catbopthings";

    public CatbopThings() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::registerModifier);
    }

    private void registerModifier(ModifierManager.ModifierRegistrationEvent event) {
        event.registerStatic(new ModifierId(MODID, "prismatic"), new PrismaticModifier());
    }
}
