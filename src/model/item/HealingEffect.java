package model.item;

import model.character.BasicCharacter;
import model.character.Statistic;
import model.combat.action.effect.AbstractActionEffect;
import model.combat.actor.ActionActor;
import model.combat.action.effect.ActionEffect;

/**
 * {@link model.combat.action.effect.ActionEffect} which heals the target by a percentage of his health.
 *
 */
public final class HealingEffect extends AbstractActionEffect implements ActionEffect {

    private final double baseHealing;

    /**
     * Construct a new effect that heals a percentage of the target health.
     * @param healingValue
     *          percentage value of health healed to to the target.
     */
    public HealingEffect(final double healingValue) {
        super();
        if (healingValue < 0.0 || healingValue > 1.0) {
            throw new IllegalStateException("Healing value must be between 0.0 and 1.0, as it is a percentage");
        }
        baseHealing = healingValue;
    }

    @Override
    public void apply(final ActionActor target) {
        if (target instanceof BasicCharacter) {
            final BasicCharacter t = ((BasicCharacter) target);
            final int roundedFlatHealing = (int) Math.round(t.getStat(Statistic.PV).getMax() * baseHealing);
            t.getStat(Statistic.PV).updateActual(roundedFlatHealing);
        } else {
            throw new IllegalArgumentException("Target must be a BasicCharacter");
        }
    }

    @Override
    public String getLogMessage() {
        return " is healed for " + baseHealing * 100 + "% max PV.";
    }

    @Override
    public String getDescription() {
        return "Heals for " + baseHealing * 100 + "% max PV.";
    }

}
