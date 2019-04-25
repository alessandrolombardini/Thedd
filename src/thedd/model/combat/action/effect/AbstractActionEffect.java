package thedd.model.combat.action.effect;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.modifier.ModifierActivation;
import thedd.model.combat.tag.Tag;

/**
 * Abstract implementation of an ActionEffect containing all the standard
 * shared behavior of Effects.
 */
public abstract class AbstractActionEffect implements ActionEffect {

    private final Set<Tag> tags = new LinkedHashSet<>();
    private final Set<Tag> permanentTags = new LinkedHashSet<>();
    private Optional<ActionActor> source = Optional.empty();
    private Optional<ActionActor> target = Optional.empty();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract void apply(ActionActor target);

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract String getLogMessage();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract String getDescription();

    /**
     * {@inheritDoc}
     */
    @Override
    public abstract String getPreviewMessage();

    /**
     * Gets a new instance of the non abstract implementation.<br>
     * The copy should only be initialized with its special attributes
     * (such as damage for DamageEffects, resistance for DamageResistanceEffects...).
     * Any attribute shared between all effects (such as tags) shall be copied and set
     * by the getCopy template method of the {@link AbstractActionEffect} class.
     * @return a partial copy of the class
     */
    protected abstract ActionEffect getSpecializedCopy();

    /**
     * {@inheritDoc}
     */
    @Override
    public final ActionEffect getCopy() {
        final ActionEffect copy = getSpecializedCopy();
        copy.addTags(getPermanentTags(), true);
        copy.addTags(getNonPermanentTags(), false);
        return copy;
    }

    @Override
    public final void updateEffectByTarget(final ActionActor target) {
        target.getEffectModifiers().stream()
                                    .filter(m -> m.getModifierActivation() == ModifierActivation.ACTIVE_ON_DEFENCE)
                                    .filter(m -> m.accept(this))
                                    .forEach(m -> m.modify(this));
    }

    @Override
    public final void updateEffectBySource(final ActionActor source) {
        source.getEffectModifiers().stream()
                                    .filter(m -> m.getModifierActivation() == ModifierActivation.ACTIVE_ON_ATTACK)
                                    .filter(m -> m.accept(this))
                                    .forEach(m -> m.modify(this));
    }

    @Override
    public final void addTags(final Set<Tag> tags, final boolean arePermanent) {
        if (arePermanent) {
            permanentTags.addAll(tags);
        } else {
            this.tags.addAll(tags.stream()
                    .filter(t -> !permanentTags.contains(t))
                    .collect(Collectors.toSet()));
        }
    }

    @Override
    public final void addTag(final Tag tag, final boolean isPermanent) {
        if (isPermanent) {
            permanentTags.add(tag);
        } else if (!permanentTags.contains(tag)) {
            tags.add(tag);
        }
    }

    @Override
    public final Set<Tag> getTags() {
        return Collections.unmodifiableSet(Stream.concat(tags.stream(), permanentTags.stream())
                .collect(Collectors.toSet()));
    }

    /**
     * Equals override: confronts two effects based on their Tags,
     * their source/target Tags, their log message and their description.
     */
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ActionEffect)) {
            return false;
        }
        final ActionEffect o = (ActionEffect) other;
        return getTags().equals(o.getTags())
                && getTarget().equals(o.getTarget())
                && getSource().equals(o.getSource())
                && getLogMessage().contentEquals(o.getLogMessage())
                && getDescription().contentEquals(o.getDescription());
    }

    /**
     * hashCode override: returns a constant number.
     */
    @Override
    public int hashCode() {
        //The only truly immutable field.
        return Objects.hash(getDescription());
    }

    @Override
    public final boolean removeTag(final Tag tag) {
        return tags.remove(tag);
    }

    @Override
    public final Optional<ActionActor> getSource() {
        return source;
    }

    @Override
    public final Optional<ActionActor> getTarget() {
        return target;
    }

    @Override
    public final void setSource(final ActionActor source) {
        this.source = Optional.of(source);
    }

    @Override
    public final void setTarget(final ActionActor target) {
        this.target = Optional.of(target);
    }

    /**
     * @return the set of permanent tags
     */
    protected final Set<Tag> getPermanentTags() {
        return Collections.unmodifiableSet(permanentTags);
    }

    /**
     * @return the set of non permanent tags
     */
    protected final Set<Tag> getNonPermanentTags() {
        return Collections.unmodifiableSet(tags);
    }

}
