package thedd.model.combat.actor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.lang3.tuple.ImmutablePair;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.modifier.Modifier;
import thedd.model.combat.modifier.ModifierActivation;
import thedd.model.combat.requirements.tags.TagRequirement;
import thedd.model.combat.requirements.tags.TagRequirementType;
import thedd.model.combat.status.Status;
import thedd.model.combat.tag.ActionTag;
import thedd.model.combat.tag.EffectTag;
import thedd.model.combat.tag.Tag;

/**
 * Abstract implementation of most of common ActionActor functionalities.
 */
public abstract class AbstractActionActor implements ActionActor {

    private final List<ImmutablePair<Modifier<Action>, Boolean>> actionModifiers = new ArrayList<>();
    private final List<ImmutablePair<Modifier<ActionEffect>, Boolean>> effectModifiers = new ArrayList<>();
    private final List<Status> statuses = new ArrayList<>();
    private final Set<Tag> permanentTags = new LinkedHashSet<>();
    private final Set<Tag> tags = new LinkedHashSet<>();
    private final Set<Action> availableActions = new LinkedHashSet<Action>();
    private final List<Action> queuedActions = new ArrayList<>();
    private final String name;
    private boolean inCombat;
    private Optional<Action> selectedAction = Optional.empty();

    @Override
    public abstract int getPriority();

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
    public Optional<Action> getSelectedAction() {
        return selectedAction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetSelectedAction() {
        selectedAction.ifPresent(a -> {
            queuedActions.remove(a);
            selectedAction = Optional.empty();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Action> getNextQueuedAction() {
        final Optional<Action> nextAction;
        if (queuedActions.isEmpty()) {
            nextAction = Optional.empty();
        } else {
            nextAction = Optional.of(queuedActions.get(0));
            queuedActions.remove(0);
        }
        return nextAction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addActionToQueue(final Action action, final boolean selectedByActor) {
        insertActionIntoQueue(queuedActions.size(), action, selectedByActor);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAvailableActions(final Set<Action> actions) {
        actions.forEach(a -> addActionToAvailable(a));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Action> getAvailableActionsList() {
        final List<Action> actions = new ArrayList<>();
        availableActions.forEach(a -> actions.add(updateAction(a.getCopy())));
        return actions;
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
    public void addActionToAvailable(final Action action) {
        final Action copy = action.getCopy();
        copy.setSource(this);
        availableActions.add(copy);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeActionFromAvailable(final Action action) {
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
            this.tags.addAll(tags.stream().filter(permanentTags::contains).collect(Collectors.toSet()));
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
    public void addActionModifier(final Modifier<Action> modifier, final boolean isPermanent) {
        actionModifiers.add(new ImmutablePair<>(modifier, isPermanent));
        modifier.addRequirement(new TagRequirement<Action>(false,
                TagRequirementType.UNALLOWED, Arrays.asList(ActionTag.IGNORES_MODIFIERS)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEffectModifier(final Modifier<ActionEffect> modifier, final boolean isPermanent) {
        effectModifiers.add(new ImmutablePair<>(modifier, isPermanent));
        modifier.addRequirement(new TagRequirement<ActionEffect>(false,
                TagRequirementType.UNALLOWED, Arrays.asList(EffectTag.IGNORES_MODIFIERS)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Modifier<Action>> getActionModifiers() {
        return Collections.unmodifiableSet(actionModifiers
                .stream()
                .map(m -> m.getKey())
                .collect(Collectors.toSet()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Modifier<ActionEffect>> getEffectModifiers() {
        return Collections.unmodifiableSet(effectModifiers
                .stream()
                .map(m -> m.getKey())
                .collect(Collectors.toSet()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeActionModifier(final Modifier<Action> modifier) {
        final Optional<ImmutablePair<Modifier<Action>, Boolean>> target = actionModifiers.stream()
                                                       .filter(m -> !m.getValue())
                                                       .filter(m -> m.getKey().equals(modifier))
                                                       .findFirst();
        target.ifPresent(actionModifiers::remove);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEffectModifier(final Modifier<ActionEffect> modifier) {
        final Optional<ImmutablePair<Modifier<ActionEffect>, Boolean>> target = effectModifiers.stream()
                                                       .filter(m -> !m.getValue())
                                                       .filter(m -> m.getKey().equals(modifier))
                                                       .findFirst();
        target.ifPresent(effectModifiers::remove);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Action> getActionQueue() {
        return Collections.unmodifiableList(queuedActions);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addStatus(final Status status) {
        final Status copy = status.getCopy();
        copy.setAfflictedActor(this);
        if (statuses.contains(copy)) {
            statuses.stream().filter(copy::equals).findFirst().get().resetCurrentDuration();
        } else {
            statuses.add(copy);
            addTags(copy.getTags(), false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeStatus(final Status status) {
        tags.removeAll(status.getTags());
        statuses.remove(status);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Status> getStatuses() {
        return Collections.unmodifiableList(statuses);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insertActionIntoQueue(final int pos, final Action action, final boolean selectedByActor) {
        if (selectedByActor) {
            if (selectedAction.isPresent()) {
                throw new IllegalStateException("The actor has already a selected action");
            }
            selectedAction = Optional.of(action);
        }
        queuedActions.add(pos, action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeActionFromQueue(final Action action) {
        queuedActions.remove(action);
        selectedAction.ifPresent(a -> {
            if (action.equals(a)) {
                selectedAction = Optional.empty();
            }
        });
    }

    /**
     * Checks whether the provided Object is comparable, then does the comparison
     * based on actors' names, tags and available actions.
     * @param other the Object to compare to this
     */
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AbstractActionActor)) {
            return false;
        }
        final AbstractActionActor o = (AbstractActionActor) other;
        return getName().equals(o.getName())
                && getTags().equals(o.getTags())
                && getAvailableActionsList().equals(o.getAvailableActionsList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        //Name is the only field which doesn't change
        //Weak hash, but there won't be many actors at the same time in the application.
        return Objects.hash(getName());
    }

    /**
     * Updates the action that has to be retrieved and all of its effects.
     * @param action the action to be updated
     * @return the updated action
     */
    private Action updateAction(final Action action) {
        getActionModifiers().stream()
                            .filter(m -> m.getModifierActivation() == ModifierActivation.RETRIEVING_ACTION)
                            .filter(m -> m.accept(action))
                            .forEach(m -> m.modify(action));
        action.getEffects().forEach(e -> {
            getEffectModifiers().stream()
                                .filter(m -> m.getModifierActivation() == ModifierActivation.RETRIEVING_ACTION)
                                .filter(m -> m.accept(e))
                                .forEach(m -> m.modify(e));
        });
        return action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetActionsQueue() {
        resetSelectedAction();
        queuedActions.clear();
    }

}
