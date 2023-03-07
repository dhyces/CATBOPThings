package catbop.catbopthings.data;

import catbop.catbopthings.CatbopThings;
import net.minecraft.data.DataGenerator;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.materials.definition.MaterialId;

public class CatbopMaterialProvider extends AbstractMaterialDataProvider {

    public CatbopMaterialProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void addMaterials() {
//        addMaterial(new MaterialId(CatbopThings.id("celesteel_test")), 4, 25, false);
    }

    @Override
    public String getName() {
        return "CATBOP Tinker's Material Provider";
    }
}
