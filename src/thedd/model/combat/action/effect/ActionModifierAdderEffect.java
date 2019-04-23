package thedd.model.combat.action.effect;

import java.util.Objects;
import java.util.Optional;

import thedd.model.combat.action.Action;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.modifier.Modifier;



/**
 * ActionEffect which adds one Modifier
 * to every actions of an ActionActor with the Tag
 * specified in the modifier.
 */
public final class ActionModifierAdderEffect extends AbstractActionEffect implements ActionEffect, RemovableEffect {

    private final Modifier<Action> modifier;
    private final boolean isPermanent;
    private Optional<ActionActor> target;

    /**
     * Create a new ActionEffect.
     * @param modifier
     *          the modifier to be added to the target
     * @param isPermanent
     *          whether the modifier is permanent
     */
    public ActionModifierAdderEffect(final Modifier<Action> modifier, final boolean isPermanent) {
        super();
        this.modifier = Objects.requireNonNull(modifier);
        this.isPermanent = isPermanent;
        target = Optional.empty();
    }

    @Override
    public String getDescription() {
        return modifier.toString();
    }

    @Override
    public void apply(final ActionActor target) {
        this.target = Optional.of(Objects.requireNonNull(target));
        this.target.get().addActionModifier(modifier, isPermanent);
    }

    @Override
    public void remove() {
        if (!target.isPresent()) {
            throw new IllegalStateException("This effect is not applied to any target;");
        } else {
            if (!isPermanent) {
                this.target.get().removeActionModifier(modifier);
            }
        }
    }

    @Override
    public String getLogMessage() {
        return getDescription().replace("Adds", "Added");
    }

    @Override
    public String getPreviewMessage() {
        return getDescription();
    }

    @Override
    public ActionEffect getSpecializedCopy() {
        return new ActionModifierAdderEffect(modifier, isPermanent);
    }
}
