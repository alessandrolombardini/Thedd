package thedd.model.item;

import java.util.Objects;
import java.util.Optional;

import model.character.BasicCharacter;
import model.character.Statistic;
import model.combat.action.effect.AbstractActionEffect;
import model.combat.actor.ActionActor;

/**
 * This {@link model.combat.action.effect.ActionEffect} which represent a change of a statistic
 * given by an {@link thedd.model.item.equipableitem.EquipableItem}.
 *
 */
public class EquipmentStatisticChangerEffect extends AbstractActionEffect implements StatisticChangerEffect {

    private Optional<BasicCharacter> target;
    private final Statistic targetStat;
    private final int effectValue;

    /**
     * Create a new effect that target a {@link model.character.Statistic}
     * and modify it by an integer value which can be positive or negative.
     * @param stat
     *  the Statistic affected
     * @param value
     *  the value used to update the statistic
     */
    public EquipmentStatisticChangerEffect(final Statistic stat, final int value) {
        targetStat = Objects.requireNonNull(stat);
        effectValue = value;
        target = Optional.empty();
    }

    @Override
    public final void apply(final ActionActor target) {
        if (target instanceof BasicCharacter) {
            if (!this.target.isPresent()) {
                this.target = Optional.of((BasicCharacter) target);
                this.target.get().getStat(targetStat).updateMax(effectValue);
            } else {
                throw new IllegalStateException("The bonus is already applied");
            }
        } else {
            throw new IllegalArgumentException("The target specified is not a BasicCharacter");
        }
    }

    @Override
    public final void removeBonus() {
        if (this.target.isPresent()) {
            this.target.get().getStat(targetStat).updateMax(-effectValue);
            this.target = Optional.empty();
        } else {
            throw new IllegalStateException("The bonus is not applied to any target");
        }
    }

    @Override
    public final String getLogMessage() {
        return targetStat.name() + ": " + effectValue;
    }

    @Override
    public final String getDescription() {
        return targetStat + ": " + (effectValue > 0 ? "+" : "-") + effectValue;
    }

}
