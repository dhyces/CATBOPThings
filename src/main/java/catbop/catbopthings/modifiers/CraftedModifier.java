package catbop.catbopthings.modifiers;

import catbop.catbopthings.CatbopThings;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.TConstruct;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.modifiers.hook.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap.Builder;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Well crafted for brass. This is a modified version of Tinker's Maintained modifier
 */
public class CraftedModifier extends Modifier implements ConditionalStatModifierHook {
    private static final Component MINING_SPEED = new TranslatableComponent(Util.makeDescriptionId("modifier", CatbopThings.id("crafted.mining_speed")));
    private static final Component VELOCITY = new TranslatableComponent(Util.makeDescriptionId("modifier", CatbopThings.id("crafted.velocity")));
    private static final Component ATTACK_DAMAGE = new TranslatableComponent(Util.makeDescriptionId("modifier", CatbopThings.id("crafted.damage")));
    /**
     * Total mining speed boost when at full durability
     */
    private static final float MINING_AT_FULL = 2;
    /**
     * Min durability to get boost
     */
    private static final float MIN_BOOST_PERCENT = 0.5f;
    /**
     * Total velocity boost when at full durability
     */
    private static final float VELOCITY_AT_FULL = 0.05f;
    /**
     * Total damage boost when at full durability
     */
    private static final float DAMAGE_AT_FULL = 1.5f;

    /**
     * Gets the total bonus for this tool at the given durability
     *
     * @param tool  Tool instance
     * @param level Tool level
     * @return Total boost
     */
    private static float getTotalBoost(IToolStackView tool, int level) {
        int durability = tool.getCurrentDurability();
        int max = tool.getStats().getInt(ToolStats.DURABILITY);
        float min = max * MIN_BOOST_PERCENT;
        if (durability > min) {
            return level * (durability - min) / (max - min);
        }
        return 0f;
    }

    @Override
    protected void registerHooks(Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TinkerHooks.CONDITIONAL_STAT);
    }

    @Override
    public void addInformation(IToolStackView tool, int level, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        boolean harvest = tool.hasTag(TinkerTags.Items.HARVEST);
        if (harvest || tool.hasTag(TinkerTags.Items.RANGED)) {
            double boost = level;
            if (tooltipKey == TooltipKey.SHIFT) {
                boost = getTotalBoost(tool, level);
            }
            if (boost > 0.01f) {
                if (harvest) {
                    addFlatBoost(MINING_SPEED, boost * MINING_AT_FULL * tool.getMultiplier(ToolStats.MINING_SPEED), tooltip);

                    addFlatBoost(ATTACK_DAMAGE, boost * DAMAGE_AT_FULL * tool.getMultiplier(ToolStats.ATTACK_DAMAGE), tooltip);
                } else {
                    addFlatBoost(VELOCITY, boost * VELOCITY_AT_FULL * tool.getMultiplier(ToolStats.VELOCITY), tooltip);
                }
            }
        }
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, int level, BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        if (isEffective) {
            event.setNewSpeed(event.getNewSpeed() + (MINING_AT_FULL * getTotalBoost(tool, level) * miningSpeedModifier * tool.getMultiplier(ToolStats.MINING_SPEED)));
        }
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity living, FloatToolStat stat, float baseValue, float multiplier) {
        if (stat == ToolStats.VELOCITY) {
            baseValue += VELOCITY_AT_FULL * getTotalBoost(tool, modifier.getLevel()) * multiplier;
        }

        if (stat == ToolStats.ATTACK_DAMAGE) {
            baseValue += DAMAGE_AT_FULL * getTotalBoost(tool, modifier.getLevel()) * multiplier;
        }
        return baseValue;
    }
}
