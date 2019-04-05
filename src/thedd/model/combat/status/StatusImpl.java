package thedd.model.combat.status;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import thedd.model.combat.action.Action;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.instance.ActionExecutionInstance;
import thedd.model.combat.tag.Tag;

/**
 * The default implementation of the {@link Status} interface.
 */
public class StatusImpl implements Status {

    private final Optional<Action> activationAction;
    private final Optional<Action> deactivationAction;
    private final StatusActivationFrequency activationType;
    private final int baseDuration;
    private int remainingTurns;
    private Optional<ActionActor> afflictedActor = Optional.empty();
    private Optional<Action> currentAction;
    private boolean updated = false;
    private Set<Tag> tags = new HashSet<>();
    private final String name;
    private boolean initialized = false;

    /**
     * Constructor for a Status.
     * @param name the name of the Status
     * @param activationAction the action provided by updating the status
     * @param deactivationAction the action provided when the status is expired
     * @param activationFrequency the frequency with which the activationaction has to be provided
     * @param duration the duration of the status (number of possible updates)
     */
    public StatusImpl(final String name, final Action activationAction, final Action deactivationAction, final StatusActivationFrequency activationFrequency, final int duration) {
        this.name = name;
        this.activationAction = Optional.ofNullable(activationAction);
        this.deactivationAction = Optional.ofNullable(deactivationAction);
        this.activationType = activationFrequency;
        baseDuration = duration;
        remainingTurns = duration;
        currentAction = Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAfflictedActor(final ActionActor actor) {
        afflictedActor = Optional.of(actor);
        activationAction.ifPresent(a -> a.setSource(afflictedActor.get()));
        deactivationAction.ifPresent(a -> a.setSource(afflictedActor.get()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public StatusActivationFrequency getActivationType() {
        return activationType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final ActionExecutionInstance instance) {
        remainingTurns--;
        updated = true;
        if (initialized && remainingTurns >= 0 && activationType == StatusActivationFrequency.ONE_TIME) { 
            currentAction = Optional.empty();
        } else {
            currentAction = remainingTurns >= 0 ? activationAction : deactivationAction;
        }
        if (currentAction.isPresent()) {
            prepareAction(instance, currentAction.get());
        }
        initialized = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getBaseDuration() {
        return baseDuration;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCurrentDuration() {
        return remainingTurns;
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
    public String getDescription() {
        //Possibly useful general idea: getLog of action by default shows the effect, but the effect has an attribute
        //"showInLog". Actions too have this attribute, so we can possibly hide "under the hood" stuff from the player.
        final StringBuilder description = new StringBuilder();
        description.append("DURATION: ");
        description.append(baseDuration > 0 ? baseDuration : " permanent");
        description.append("\n");
        if (activationAction.isPresent()) {
            String text = activationType == StatusActivationFrequency.ONE_TIME ? "ONE TIME:" : "EVERY ROUND:";
            description.append(text);
            description.append("\n");
            if (afflictedActor.isPresent()) {
                description.append(activationAction.get().getEffectsPreview(afflictedActor.get()));
            } else {
                description.append(activationAction.get().getEffectsPreview(null));
            }
            description.append("\n");
        }
        if (deactivationAction.isPresent()) {
            String text = "WHEN EXPIRED";
            description.append(text);
            description.append("\n");
            if (afflictedActor.isPresent()) {
                description.append(deactivationAction.get().getEffectsPreview(afflictedActor.get()));
            } else {
                description.append(deactivationAction.get().getEffectsPreview(null));
            }
            description.append("\n");
        }
        return description.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPermanent() {
        return baseDuration <= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTag(final Tag tag) {
        tags.add(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTags(final Set<Tag> tags) {
        this.tags.addAll(tags);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Status)) {
            return false;
        }
        final Status o = (Status) other;
        return getTags().equals(o.getTags())
                && getName().equals(o.getName())
                && getBaseDuration() == o.getBaseDuration()
                && getAfflictedActor().equals(o.getAfflictedActor());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ActionActor> getAfflictedActor() {
        return afflictedActor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUpdated() {
        return updated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsUpdated(final boolean isUpdated) {
        updated = isUpdated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getTags(), getName(), getBaseDuration()); //etc...
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
    public Status getCopy() {
        final Action action1 = activationAction.isPresent() ? activationAction.get().getCopy() : null;
        final Action action2 = deactivationAction.isPresent() ? deactivationAction.get().getCopy() : null;
        final Status copy = new StatusImpl(this.name, action1, action2, this.activationType, this.baseDuration);
        copy.addTags(getTags());
        return copy;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetCurrentDuration() {
        updated = false;
        remainingTurns = baseDuration;
    }

    /**
     * Sets the targets of the provided action.
     * @param instance the instance in which the action has to be executed
     * @param action the action to be prepared
     */
    protected void prepareAction(final ActionExecutionInstance instance, final Action action) {
        //Can be overridden to personalize stats behaviour (ex: set afflictedActor as source but target the enemy
        //party with an AOE life leech action. (Or maybe your own party)
        final List<ActionActor> availableTargets = action.getValidTargets(instance);
        action.setTargets(afflictedActor.get(), availableTargets);
    }

}
