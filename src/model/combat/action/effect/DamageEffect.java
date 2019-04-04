package model.combat.action.effect;

import java.util.Objects;
import java.util.stream.Collectors;

import model.combat.actor.ActionActor;


/**
 * An {@link ActionEffect} which deals damage to a Character.
 */
public class DamageEffect extends AbstractActionEffect {

    private final double baseDamage;
    private double damage;
    private double dealtDamage;

    /**
     * @param baseAmount the base amount of damage dealt
     */
    public DamageEffect(final double baseAmount) {
        super();
        baseDamage = baseAmount;
        damage = baseAmount;
    }

    /**
     * If the target is a Character, reduces its' health value.
     */
    @Override
    public final void apply(final ActionActor target) {
        dealtDamage = damage;
        //if target is Character -> target.modifyHealth(amount)
        //Do stuff
        damage = baseDamage;
    }

    @Override
    public final String getLogMessage() {
        return "Dealt " + dealtDamage + " HP damage ";
    }

    @Override
    public final String getPreviewMessage() {
        getSource().ifPresent(this::updateEffectBySource);
        getTarget().ifPresent(this::updateEffectByTarget);
        final String result =  "Deals " + damage + " HP damage ";
        damage = baseDamage;
        return result;
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
        //This appends all the effect tags at the end of the description, maybe it would be better to
        //put the tags only on the action description.
        return "Deals " + damage + " HP damage \nTags:" + getTags().stream()
                                                            .map(t -> t.getLiteral()).collect(Collectors.joining(", "));
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
}
