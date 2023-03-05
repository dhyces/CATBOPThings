package catbop.catbopthings;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import net.minecraft.world.item.ItemStack;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolRebuildContext;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.tools.TinkerModifiers;

public class PrismiteModifier extends Modifier {

    @Override
    public void addVolatileData(@NotNull ToolRebuildContext context, int level, @NotNull ModDataNBT volatileData) {
        TinkerModifiers.overslime.get().setFriend(volatileData);
    }

    @Override
    public void onInventoryTick(@NotNull IToolStackView tool, int level, Level world, @NotNull LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, @NotNull ItemStack stack) {
        // update 1 times a second, but skip when active (messes with pulling bow back)
        if (!world.isClientSide && holder.tickCount % 20 == 0 && holder.getUseItem() != stack) {
            // check skylight
            // note this may go negative, that is not a problem
            int skylight = world.getBrightness(LightLayer.SKY, holder.blockPosition()) - world.getSkyDarken();
            // 1 if the celestial conduit is applied, 0 otherwise. Lvl will never be >1
            int conduitboost = tool.getModifierLevel(new ModifierId(CatbopThings.MODID, "celestial_conduit"));
            // same as above but for celesteel
            int celesteelboost = tool.getModifierLevel(new ModifierId(CatbopThings.MODID, "celesteel"));
            if ((skylight + conduitboost) > 0) {
                // has a chance of restoring durability depending on several factors
                // its kinda complicated but you can probably get a general idea by reading the equations
                if (stack.getDamageValue() < stack.getMaxDamage() && RANDOM.nextFloat() < (((level/3.0) * 0.01 * skylight) + ((level/3.0) * 0.05 * conduitboost))) {
                    ToolDamageUtil.repair(tool, (1 + celesteelboost));
                }
            }
        }
    }
}