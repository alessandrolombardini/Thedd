package thedd.model.combat.modifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import thedd.model.combat.common.Modifiable;
import thedd.model.combat.requirements.Requirement;

/**
 * Abstract implementations of Modifiers' basic behavior.
 * @param <T> the type of the accepted modifiable entities
 */
public abstract class AbstractModifier<T extends Modifiable> implements Modifier<T> {

    private ModifierActivation type;
    private final List<Requirement<T>> requirements = new ArrayList<>();

    /**
     * Constructor for the abstract class.
     * @param type declares whether this modifier should be applied on attack, defense or every time
     */
    protected AbstractModifier(final ModifierActivation type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModifierActivation getModifierActivation() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModifierActivation(final ModifierActivation type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean accept(final T modifiable) {
        if (modifiable == null) {
            return false;
        }
        return getRequirements().stream().allMatch(r -> r.isFulfilled(modifiable));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void modify(T modifiable);

    /**
     * {@inheritDoc}
     */
    @Override
    public void addRequirement(final Requirement<T> requirement) {
        requirements.add(requirement);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Requirement<T>> getRequirements() {
        return Collections.unmodifiableList(requirements);
    }

}
