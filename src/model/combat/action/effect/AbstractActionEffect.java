package model.combat.action.effect;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.combat.actor.ActionActor;
import model.combat.modifier.ModifierActivation;
import model.combat.tag.Tag;

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
     * {@inheritDoc}
     */
    @Override
    public void updateEffectByTarget(final ActionActor target) {
        this.target = Optional.of(target);
        target.getEffectModifiers().stream()
                                    .filter(m -> m.getModifierActivation() == ModifierActivation.ACTIVE_ON_DEFENCE
                                            || m.getModifierActivation() == ModifierActivation.ALWAYS_ACTIVE)
                                    .filter(m -> m.accept(this))
                                    .forEach(m -> m.modify(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateEffectBySource(final ActionActor source) {
        this.source = Optional.of(source);
        source.getEffectModifiers().stream()
                                    .filter(m -> m.getModifierActivation() == ModifierActivation.ACTIVE_ON_ATTACK
                                            || m.getModifierActivation() == ModifierActivation.ALWAYS_ACTIVE)
                                    .filter(m -> m.accept(this))
                                    .forEach(m -> m.modify(this));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTags(final Set<Tag> tags, final boolean arePermanent) {
        if (arePermanent) {
            permanentTags.addAll(tags);
        } else {
            tags.addAll(tags.stream()
                    .filter(permanentTags::contains)
                    .collect(Collectors.toSet()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTag(final Tag tag, final boolean isPermanent) {
        if (isPermanent) {
            permanentTags.add(tag);
        } else if (!permanentTags.contains(tag)) {
            tags.add(tag);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> getTags() {
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
        //No fields in this class are immutable. Furthermore, Action effects are supposed
        //to be used in collections not based on hashing (such as Lists).
        //This solution, while being far from optimal, does follow the rules dictated by the method's contract
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeTag(final Tag tag) {
        return tags.remove(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ActionActor> getSource() {
        return source;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ActionActor> getTarget() {
        return target;
    }

}
