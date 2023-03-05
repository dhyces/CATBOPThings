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

public class PrismaticModifier extends Modifier {

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
            // 1 if the celestial conduit is applied, 0 otherwise. Level is never >1
            int conduitboost = tool.getModifierLevel(new ModifierId(CatbopThings.MODID, "celestial_conduit"));
            if ((skylight + conduitboost) > 0) {
                // has a chance of restoring durability depending on several factors
                // in full sun, 1 level prismatic = overgrowth, 3 levels + conduit = 5x overgrowth
                if (stack.getDamageValue() < stack.getMaxDamage() && RANDOM.nextFloat() < (((level/3.0) * 0.01 * skylight) + ((level/3.0) * 0.10 * conduitboost))) {
                    ToolDamageUtil.repair(tool, 1);
                }
            }
        }
    }
}