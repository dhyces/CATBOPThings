package catbop.catbopthings;

import net.minecraft.util.Mth;
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
            int conduitBoost = tool.getModifierLevel(new ModifierId(CatbopThings.id("celestial_conduit")));
            if ((skylight > 0) || (conduitBoost > 0)) {
                // same as above but for celesteel
                int celesteelBoost = tool.getModifierLevel(new ModifierId(CatbopThings.id("celesteel")));
                // has a chance of restoring durability depending on several factors
                // its kinda complicated, but you can probably get a general idea by reading the equations
                float skylightChance = Mth.clamp((level/3.0f) * 0.01f * skylight, 0f, 1f);
                float conduitChance = Mth.clamp((level/3.0f) * 0.05f * conduitBoost, 0f, 1f);
                if (stack.getDamageValue() < stack.getMaxDamage() && RANDOM.nextFloat() <= skylightChance + conduitChance) {
                    ToolDamageUtil.repair(tool, (1 + celesteelBoost));
                }
            }
        }
    }
}