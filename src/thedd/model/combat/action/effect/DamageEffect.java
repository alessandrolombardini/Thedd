package thedd.model.combat.action.effect;

import java.util.Objects;
import java.util.stream.Collectors;

import thedd.model.character.BasicCharacter;
import thedd.model.character.statistics.Statistic;
import thedd.model.combat.actor.ActionActor;


/**
 * An {@link ActionEffect} which deals damage to a Character.
 */
public class DamageEffect extends AbstractActionEffect {

    private final int baseDamage;
    private int damage;
    private int dealtDamage;

    /**
     * @param baseAmount the base amount of damage dealt
     */
    public DamageEffect(final double baseAmount) {
        super();
        baseDamage = (int) Math.round(baseAmount);
        damage = baseDamage;
    }

    /**
     * If the target is a Character, reduces its' health value.
     */
    @Override
    public final void apply(final ActionActor target) {
        dealtDamage = damage;
        if (target instanceof BasicCharacter) {
            ((BasicCharacter) target).getStat(Statistic.HEALTH_POINT).updateActual(-damage);
        }
        damage = baseDamage;
    }

    @Override
    public final String getLogMessage() {
        return appendTags("Dealt " + dealtDamage + " HP damage ");
    }

    @Override
    public final String getPreviewMessage() {
        getSource().ifPresent(this::updateEffectBySource);
        getTarget().ifPresent(this::updateEffectByTarget);
        final String result =  "Deals " + damage + " HP damage ";
        damage = baseDamage;
        return appendTags(result);
    }

    /**
     * Adds a value to the damage that is going to be dealt.
     * @param damage the amount to sum to the current damage
     */
    public void addToDamage(final double damage) {
        this.damage += damage;
    }

    /**
     * Gets the base damage value.
     * @return the base damage
     */
    public double getBaseDamage() {
        return baseDamage;
    }

    /**
     * Gets the current damage value.
     * @return the current damage
     */
    public double getDamage() {
        return damage;
    }

    /**
     * Gets a description of the effect.
     * @return a description of the effect
     */
    @Override
    public String getDescription() {
        return appendTags("Deals " + damage + " HP damage ");
    }

    /**
     * Calls the parent implementation and
     * confronts the base and current damage.
     * @return true if the two entities are equal
     */
    @Override
    public final boolean equals(final Object other) {
        if (super.equals(other) && other instanceof DamageEffect) {
            final DamageEffect o = (DamageEffect) other;
            return getBaseDamage() == o.getBaseDamage()
                    && getDamage() == o.getDamage();
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return Objects.hash(super.hashCode(), getBaseDamage());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionEffect getCopy() {
        final ActionEffect copy = new DamageEffect(baseDamage);
        copy.addTags(getPermanentTags(), true);
        copy.addTags(getNonPermanentTags(), false);
        if (Double.compare(damage, baseDamage) != 0) {
            ((DamageEffect) copy).addToDamage(damage - baseDamage);
        }
        return copy;
    }

    private String appendTags(final String original) {
        if (getTags().stream().filter(t -> !t.isHidden()).count() <= 0) {
            return original;
        } else {
            return original.concat(getTags().stream()
                                            .filter(t -> !t.isHidden())
                                            .map(t -> t.getLiteral())
                                            .collect(Collectors.joining("/", "[", "]")));
        }
    }
}