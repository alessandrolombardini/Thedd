package model.combat.actor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.combat.action.Action;
import model.combat.modifier.AbstractActionModifier;
import model.combat.modifier.AbstractEffectModifier;
import model.combat.modifier.Modifier;
import model.combat.tag.Tag;

/**
 * Abstract implementation of most of common ActionActor functionalities.
 */
public abstract class AbstractActionActor implements ActionActor {

    private final String name;
    private final List<Modifier> permanentModifiers = new ArrayList<>();
    private final List<Modifier> modifiers = new ArrayList<>();
    private final Set<Tag> permanentTags = new LinkedHashSet<>();
    private final Set<Tag> tags = new LinkedHashSet<>();
    private Optional<Action> currentAction;
    private final Set<Action> availableActions = new LinkedHashSet<Action>();
    private int roundPlace;
    private boolean inCombat;

    /**
     * Constructor of the class.
     * @param name the name of the actor.
     */
    public AbstractActionActor(final String name) {
        this.name = name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Action> getAction() {
        return currentAction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAction(final Action action) {
        currentAction = Optional.ofNullable(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int compareTo(final ActionActor other) {
        return this.getPriority() - other.getPriority();
    }

    @Override
    public abstract int getPriority();

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAvailableActions(final Set<? extends Action> actions) {
        actions.forEach(a -> addAction(a));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Action> getAvailableActionsList() {
        return Collections.unmodifiableSet(availableActions).stream().collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Action> getAvailableActionsSet() {
        return Collections.unmodifiableSet(availableActions);
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AbstractActionActor)) {
            return false;
        }
        final AbstractActionActor o = ((AbstractActionActor) other);
        return getName().equals(o.getName())
                && getTags().equals(o.getTags())
                && getAvailableActionsSet().equals(o.getAvailableActionsSet());
    }

    @Override
    public int hashCode() {
        //Name is the only field which doesn't change
        return Objects.hash(getName());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsInCombat(final boolean isInCombat) {
        this.inCombat = isInCombat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInCombat() {
        return inCombat;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPlaceInRound() {
        return roundPlace;
    }

    /**
     * {@inheritDoc}
     * {@throws IllegalArgumentException} if the provided number is <= 0
     */
    @Override
    public void setPlaceInRound(final int place) {
        if (place > 0) {
            roundPlace = place;
        } else {
            throw new IllegalArgumentException("Number must be greater than 0");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetPlaceInRound() {
        roundPlace = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAction(final Action action) {
        availableActions.add(action);
        action.setSource(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeAction(final Action action) {
        return availableActions.remove(action);
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
     * {@inheritDoc}
     */
    @Override
    public void addTags(final Set<Tag> tags, final boolean arePermanent) {
        if (arePermanent) {
            permanentTags.addAll(tags);
        } else {
            tags.addAll(tags.stream().filter(permanentTags::contains).collect(Collectors.toSet()));
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
    public boolean removeTag(final Tag tag) {
        return tags.remove(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addModifier(final Modifier modifier, final boolean isPermanent) {
        if (isPermanent) {
            permanentModifiers.add(modifier);
        } else if (!permanentModifiers.contains(modifier)){
            modifiers.add(modifier);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Modifier> getActionModifiers() {
        return Collections.unmodifiableSet(Stream.concat(permanentModifiers.stream()
                .filter(m -> m instanceof AbstractActionModifier)
                .map(m -> (AbstractActionModifier) m),
                modifiers.stream()
                .filter(m -> m instanceof AbstractActionModifier)
                .map(m -> (AbstractActionModifier) m))
                .collect(Collectors.toSet()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Modifier> getEffectModifiers() {
        return Collections.unmodifiableSet(Stream.concat(permanentModifiers.stream()
                .filter(m -> m instanceof AbstractEffectModifier)
                .map(m -> (AbstractEffectModifier) m),
                modifiers.stream()
                .filter(m -> m instanceof AbstractEffectModifier)
                .map(m -> (AbstractEffectModifier) m))
                .collect(Collectors.toSet()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeModifier(final Modifier modifier) {
        modifiers.remove(modifier);
    }

}
