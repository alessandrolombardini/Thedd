package thedd.model.combat.action.effect;

import java.util.Objects;
import java.util.Optional;

import thedd.model.character.BasicCharacter;
import thedd.model.character.statistics.Statistic;
import thedd.model.combat.actor.ActionActor;


/**
 * This {@link model.combat.action.effect.ActionEffect} which represent a change of a statistic
 * given by an {@link thedd.model.item.equipableitem.EquipableItem}.
 *
 */
public final class EquipmentStatisticChangerEffect extends AbstractActionEffect implements RemovableEffect {

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
    public void apply(final ActionActor target) {
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
    public void removeBonus() {
        if (this.target.isPresent()) {
            this.target.get().getStat(targetStat).updateMax(-effectValue);
            this.target = Optional.empty();
        } else {
            throw new IllegalStateException("The bonus is not applied to any target");
        }
    }

    @Override
    public String getLogMessage() {
        return targetStat.name() + ": " + effectValue;
    }

    @Override
    public String getDescription() {
        return (effectValue > 0 ? "Adds " : "Subtract ") + Math.abs(effectValue) 
               + (effectValue > 0 ? " to " : " from ") + targetStat;
    }

    @Override
    public String getPreviewMessage() {
        return getDescription();
    }

    @Override
    public ActionEffect getCopy() {
        return new EquipmentStatisticChangerEffect(targetStat, effectValue);
    }

}
