package model.item;

import java.util.Objects;
import java.util.Optional;

import model.combat.action.effect.AbstractActionEffect;
import model.combat.action.effect.ActionEffect;
import model.combat.actor.ActionActor;
import model.combat.modifier.Modifier;

/**
 * {@link model.combat.action.effect.ActionEffect} which adds one {@link model.combat.modifier.Modifier}
 * to every actions of an {@link model.combat.actor.ActionActor} with the {@link model.combat.tag.Tag}
 * specified in the modifier.
 */
public class ModifierAdderEffect extends AbstractActionEffect implements ActionEffect, StatisticChangerEffect {

    private final Modifier modifier;
    private final boolean isPermanent;
    private Optional<ActionActor> target;

    /**
     * Create a new {@link model.combat.action.effect.ActionEffect}.
     * @param modifier
     *          the modifier to be added to the target
     * @param isPermanent
     *          whether the modifier is permanent
     */
    public ModifierAdderEffect(final Modifier modifier, final boolean isPermanent) {
        this.modifier = modifier;
        this.isPermanent = isPermanent;
        target = Optional.empty();
    }

    @Override
    public final String getDescription() {
        return modifier.toString();
    }

    @Override
    public final void apply(final ActionActor target) {
        this.target = Optional.of(Objects.requireNonNull(target));
        this.target.get().addModifier(modifier, isPermanent);
    }

    @Override
    public final void removeBonus() {
        if (!target.isPresent()) {
            throw new IllegalStateException("This effect is not applied to any target;");
        } else {
            if (!isPermanent) {
                this.target.get().removeModifier(modifier);
            }
        }
    }

    @Override
    public final String getLogMessage() {
        return null;
    }
}
