package thedd.model.combat.action.effect;

import java.util.Objects;

import thedd.model.character.BasicCharacter;
import thedd.model.character.statistics.Statistic;
import thedd.model.combat.actor.ActionActor;

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
        if (Objects.requireNonNull(target) instanceof BasicCharacter) {
            final BasicCharacter t = ((BasicCharacter) target);
            final int roundedFlatHealing = (int) Math.round(t.getStat(Statistic.HEALTH_POINT).getMax() * baseHealing);
            t.getStat(Statistic.HEALTH_POINT).updateActual(roundedFlatHealing);
        } else {
            throw new IllegalArgumentException("Target must be a BasicCharacter");
        }
    }

    @Override
    public String getLogMessage() {
        return "Healed for " + baseHealing * 100 + "% max HP.";
    }

    @Override
    public String getDescription() {
        return "Heals for " + baseHealing * 100 + "% max HP.";
    }

    @Override
    public String getPreviewMessage() {
        return getDescription();
    }

    @Override
    public ActionEffect getSpecializedCopy() {
        return new HealingEffect(baseHealing);
    }

}
