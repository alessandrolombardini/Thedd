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
        super();
        targetStat = Objects.requireNonNull(stat);
        effectValue = value;
        target = Optional.empty();
    }

    @Override
    public void apply(final ActionActor target) {
        if (target instanceof BasicCharacter) {
            if (!this.target.isPresent()) {
                this.target = Optional.of((BasicCharacter) target);
                if (targetStat == Statistic.HEALTH_POINT) {
                    this.target.get().getStat(targetStat).updateMax(effectValue);
                } else {
                    this.target.get().getStat(targetStat).updateActual(effectValue);
                }
            } else {
                throw new IllegalStateException("The bonus is already applied");
            }
        } else {
            throw new IllegalArgumentException("The target specified is not a BasicCharacter");
        }
    }

    @Override
    public void remove() {
        if (this.target.isPresent()) {
            if (targetStat == Statistic.HEALTH_POINT) {
                this.target.get().getStat(targetStat).updateMax(-effectValue);
            } else {
                this.target.get().getStat(targetStat).updateActual(-effectValue);
            }
            this.target = Optional.empty();
        } else {
            throw new IllegalStateException("The bonus is not applied to any target");
        }
    }

    @Override
    public String getLogMessage() {
        return (effectValue > 0 ? "Added " : "Subtracted") + effectValue 
                + (effectValue > 0 ? " to " : " from ") + targetStat.name();
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + effectValue;
        result = prime * result + ((targetStat == null) ? 0 : targetStat.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EquipmentStatisticChangerEffect other = (EquipmentStatisticChangerEffect) obj;
        return (effectValue == other.effectValue && targetStat == other.targetStat);
    }

}
