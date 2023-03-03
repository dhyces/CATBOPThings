package catbop.catbopthings;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;
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
  public void onInventoryTick(IToolStackView tool, int level, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, @NotNull ItemStack stack) {
    // update 1 times a second, but skip when active (messes with pulling bow back)
    if (!world.isClientSide && holder.tickCount % 20 == 0 && holder.getUseItem() != stack) {
      // check skylight
      // note this may go negative, that is not a problem
      int skylight = world.getBrightness(LightLayer.SKY, holder.blockPosition()) - world.getSkyDarken();
      //adds 5 if the tool has the soul light (WIP name)
      //skylight += tool.getModifierLevel(ModiferId) * 5;
      if (skylight > 5) {
        // has a 5% chance of restoring each second per level
        if (stack.getDamageValue() < stack.getMaxDamage() && RANDOM.nextFloat() < (level * 0.01 * skylight)) {
          ToolDamageUtil.repair(tool, 1);
        }
      }
    }
  }
}