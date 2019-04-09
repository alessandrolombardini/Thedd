package thedd.model.combat.action.effect;

import java.util.Objects;
import java.util.Optional;

import thedd.model.combat.action.Action;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.modifier.Modifier;



/**
 * {@link model.combat.action.effect.ActionEffect} which adds one {@link model.combat.modifier.Modifier}
 * to every actions of an {@link model.combat.actor.ActionActor} with the {@link model.combat.tag.Tag}
 * specified in the modifier.
 */
public final class ActionModifierAdderEffect extends AbstractActionEffect implements ActionEffect, RemovableEffect {

    private final Modifier<Action> modifier;
    private final boolean isPermanent;
    private Optional<ActionActor> target;

    /**
     * Create a new {@link model.combat.action.effect.ActionEffect}.
     * @param modifier
     *          the modifier to be added to the target
     * @param isPermanent
     *          whether the modifier is permanent
     */
    public ActionModifierAdderEffect(final Modifier<Action> modifier, final boolean isPermanent) {
        this.modifier = modifier;
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
    public void removeBonus() {
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
        return getDescription();
    }

    @Override
    public String getPreviewMessage() {
        return getDescription();
    }

    @Override
    public ActionEffect getCopy() {
        return new ActionModifierAdderEffect(modifier, isPermanent);
    }
}
