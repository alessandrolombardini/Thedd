package model.item;

import model.character.BasicCharacter;
import model.character.Statistic;
import model.combat.interfaces.ActionActor;
import model.combat.interfaces.ActionEffect;

/**
 * ActionEffect which heals the target.
 *
 */
public class HealingEffect implements ActionEffect {

    private final double baseHealing;

    public HealingEffect(final double healingValue) {
        baseHealing = healingValue;
    }

    @Override
    public final void apply(final ActionActor target) {
        if (target instanceof BasicCharacter) {
            final BasicCharacter t = ((BasicCharacter) target);
            if(baseHealing != Double.POSITIVE_INFINITY) {
                t.getStat(Statistic.PV).updateActual((int) baseHealing);
            } else {
                t.getStat(Statistic.PV).updateActual(t.getStat(Statistic.PV).getMax());
            }
        } else {
            throw new IllegalArgumentException("Target must be a BasicCharacter");
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
        return " is healed for " + baseHealing + " PV.";
    }

}
