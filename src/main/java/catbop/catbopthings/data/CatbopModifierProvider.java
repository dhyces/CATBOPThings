package catbop.catbopthings.data;

import catbop.catbopthings.CatbopThings;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.json.JsonRedirect;
import slimeknights.tconstruct.library.modifiers.ModifierId;

public class CatbopModifierProvider extends AbstractModifierProvider {
    public CatbopModifierProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addModifiers() {
//        addModifier(new ModifierId(CatbopThings.id("prismite_test")), null, new JsonRedirect(CatbopThings.id("prismite"), null));
    }

    @Override
    public String getName() {
        return "CATBOP Tinker's Modifier Provider";
    }
}
