package thedd.model.combat.status;

import java.util.Optional;
import java.util.Set;

import thedd.model.combat.action.Action;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.instance.ActionExecutionInstance;
import thedd.model.combat.tag.Tag;

/**
 * An entity that holds up to two {@link Action} and one (or more) {@link Tag}.<br>
 * It can be attached to an {@link ActionActor}.<p>
 * A status represents a particular condition afflicting an actor: it has a duration
 * and may provide an action.<p>
 */
public interface Status {

    /**
     * Gets the activation type of the status.
     * @return the activation type
     */
    StatusActivationFrequency getActivationType();

    /**
     * Gets the base duration of the status.
     * @return the base duration
     */
    int getBaseDuration();

    /**
     * Gets the current duration of the status 
     * (how many updates are left).
     * @return the current duration
     */
    int getCurrentDuration();

    /**
     * Gets the current action provided by the
     * status (if any).
     * @return the current action
     */
    Optional<Action> getAction();

    /**
     * Updates the duration of the status and
     * prepares the next action to be provided
     * by {@link #getAction()}.<p>
     * Sets the value returned by {@link #isUpdated()} to true.
     * @param instance the instance on which this status is updated
     */
    void update(ActionExecutionInstance instance);

    /**
     * Gets a description of the status.
     * @return a description of the status
     */
    String getDescription();

    /**
     * Gets whether the status is permanent.
     * @return true if the status is permanent, false otherwise
     */
    boolean isPermanent();

    /**
     * Sets the target {@link ActionActor} of this
     * status.
     * @param actor the actor to which this status will be applied
     */
    void setAfflictedActor(ActionActor actor);

    /**
     * Adds a {@link Tag} to the status.
     * @param tag the tag to be added
     */
    void addTag(Tag tag);

    /**
     * Adds one or more {@link Tag} to the status.
     * @param tags the tags to be added
     */
    void addTags(Set<Tag> tags);

    /**
     * Gets the tags of the status.
     * @return the tags of the status
     */
    Set<Tag> getTags();

    /**
     * Gets the name of the status.
     * @return the name of the status
     */
    String getName();

    /**
     * Gets a copy of the status.
     * @return the copy of the status
     */
    Status getCopy();

    /**
     * Gets the {@link ActionActor} to which the status
     * is applied.
     * @return the afflicted actor
     */
    Optional<ActionActor> getAfflictedActor();

    /**
     * Gets whether the status has been updated.
     * @return true if the status has been updated, false otherwise
     */
    boolean isUpdated();

    /**
     * Sets whether the status is currently updated.
     * @param isUpdated whether the status is currently updated
     */
    void setIsUpdated(boolean isUpdated);

    /**
     * Resets the current duration of the status and sets
     * the value returned by {@link #isUpdated()} to false.
     */
    void resetCurrentDuration();

}
