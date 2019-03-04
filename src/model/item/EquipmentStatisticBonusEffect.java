package model.item;

import java.util.Objects;
import java.util.Optional;

import model.character.BasicCharacter;
import model.character.Statistic;
import model.combat.interfaces.ActionActor;

/**
 * An actionEffect which represent a bonus (or malus) given by an EquipableItem.
 *
 */
public class EquipmentStatisticBonusEffect implements StatisticBonusEffect {

    private Optional<BasicCharacter> target;
    private final Statistic targetStat;
    private final int effectValue;

    /**
     * 
     * @param stat
     *  the Statistic affected
     * @param value
     *  the value used to update the statistic
     */
    public EquipmentStatisticBonusEffect(final Statistic stat, final int value) {
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
    public void updateEffectByTarget(final ActionActor target) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateEffectBySource(final ActionActor source) {
        // TODO Auto-generated method stub

    }

    @Override
    public final String getLogMessage() {
        return targetStat.name() + ": " + effectValue;
    }

}
