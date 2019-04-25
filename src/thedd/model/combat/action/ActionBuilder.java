package thedd.model.combat.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.action.executionpolicies.DefaultExecutionPolicy;
import thedd.model.combat.action.executionpolicies.ExecutionPolicy;
import thedd.model.combat.action.targeting.ActionTargeting;
import thedd.model.combat.action.targeting.DefaultTargeting;
import thedd.model.combat.actor.ActionActor;

/**
 * Builder for an {@link Action}.<p>
 * 
 * Unset fields have the following default values:<br>
 *  -Source: null (the resulting action will thus have Optional.empty() as value)<br>
 *  -Name: "[Name missing]"<br>
 *  -Description: "[Description missing]"<br>
 *  -Effects: an empty list<br>
 *  -Category: {@link ActionCategory#STANDARD}<br>
 *  -LogMessageType: {@link LogMessageTypeImpl#STANDARD_ACTION}<br>
 *  -ActionTargeting: {@link DefaultTargeting}<br>
 *  -ExecutionPolicy: {@link DefaultExecutionPolicy}<br>
 *  -TargetType: {@link TargetType#EVERYONE}<br>
 *  -BaseHitChance: 0d
 */
public class ActionBuilder {

    private ActionActor source = null;
    private String name = "[Name missing]";
    private String description = "[Description missing]";
    private List<ActionEffect> effects = new ArrayList<>();
    private ActionCategory category = ActionCategory.STANDARD;
    private LogMessageType logMessageType = LogMessageTypeImpl.STANDARD_ACTION;
    private ActionTargeting targeting = new DefaultTargeting();
    private ExecutionPolicy applyPolicy = new DefaultExecutionPolicy();
    private TargetType targetType = TargetType.EVERYONE;
    private double baseHitChance = 0d;

    /**
     * Sets the target type.
     * @param targetType the target type
     * @return the updated builder
     */
    public ActionBuilder setTargetType(final TargetType targetType) {
        this.targetType = targetType;
        return this;
    }

    /**
     * Sets the source of the action.
     * @param source the source of the action
     * @return the updated builder
     */
    public ActionBuilder setSource(final ActionActor source) {
        this.source = Objects.requireNonNull(source);
        return this;
    }

    /**
     * Sets the base hit chance of the action.
     * @param baseHitChance the base hit chance of the action
     * @return the updated builder
     */
    public ActionBuilder setBaseHitChance(final double baseHitChance) {
        this.baseHitChance = baseHitChance;
        return this;
    }

    /**
     * Sets the name of the action.
     * @param name the name of the action
     * @return the updated builder
     */
    public ActionBuilder setName(final String name) {
        this.name = Objects.requireNonNull(name);
        return this;
    }

    /**
     * Sets the description of the action.
     * @param description the description of the action
     * @return the updated builder
     */
    public ActionBuilder setDescription(final String description) {
        this.description = Objects.requireNonNull(description);
        return this;
    }

    /**
     * Sets the effects of the action.
     * @param effects the effects of the action
     * @return the updated builder
     */
    public ActionBuilder setEffects(final List<ActionEffect> effects) {
        this.effects = Objects.requireNonNull(effects);
        return this;
    }

    /**
     * Sets the category of the action.
     * @param category the category of the action
     * @return the updated builder
     */
    public ActionBuilder setCategory(final ActionCategory category) {
        this.category = Objects.requireNonNull(category);
        return this;
    }

    /**
     * Sets the log message format of the action.
     * @param logType the log type of the action
     * @return the updated builder
     */
    public ActionBuilder setLogMessage(final LogMessageType logType) {
        this.logMessageType = Objects.requireNonNull(logType);
        return this;
    }

    /**
     * Sets the targeting policy of the action.
     * @param targetingPolicy the targetingPolicy of the action
     * @return the updated builder
     */
    public ActionBuilder setTargetingPolicy(final ActionTargeting targetingPolicy) {
        this.targeting = Objects.requireNonNull(targetingPolicy);
        return this;
    }

    /**
     * Sets how the action will behave upon execution.
     * @param executionPolicy the execution policy of the action
     * @return the updated builder
     */
    public ActionBuilder setExecutionPolicy(final ExecutionPolicy executionPolicy) {
        this.applyPolicy = Objects.requireNonNull(executionPolicy);
        return this;
    }

    /**
     * Builds the action.
     * @return the finished action
     */
    public Action build() {
        return new ActionImpl(source, name, effects, category,
                              targeting, baseHitChance, targetType,
                              description, logMessageType, applyPolicy);
    }
}
