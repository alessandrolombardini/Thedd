package thedd.model.combat.modifier;

import java.util.List;

import thedd.model.combat.action.Action;
import thedd.model.combat.action.effect.DamageEffect;
import thedd.model.combat.requirements.Requirement;
import thedd.model.combat.tag.Tag;

/**
 * Modifier which adds an additional DamageEffect to an action.
 */
public final class DamageAdderModifier extends AbstractModifier<Action> implements Modifier<Action> {

    private final Tag addedTag;
    private final double value;

    /**
     * 
     * @param value
     *          the damage added to the action
     * @param requirements
     *          the requirements needed by actions to get the damage added
     * @param addedTag
     *          additional tags added to the action modified
     */
    public DamageAdderModifier(final double value, final List<Requirement<Action>> requirements, final Tag addedTag) {
        super(ModifierActivation.RETRIEVING_ACTION);
        this.value = value;
        this.addedTag = addedTag;
        requirements.forEach(this::addRequirement);
    }

    @Override
    public void modify(final Action modifiable) {
        if (modifiable.getEffects().stream().filter(ae -> ae instanceof DamageEffect)
                                            .filter(e -> e.getTags().contains(addedTag))
                                            .peek(e -> ((DamageEffect) e).addToDamage(value))
                                            .count() <= 0) {
            final DamageEffect dm = new DamageEffect(value);
            dm.addTag(addedTag, false);
            modifiable.addEffect(dm);
        }
    }

    @Override
    public String toString() {
        return "Adds " + value + " to " + addedTag.getLiteral();
    }

}
