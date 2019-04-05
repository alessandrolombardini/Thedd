package thedd.model.combat.action;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.instance.ActionExecutionInstance;
import thedd.model.combat.tag.Tag;

/**
 * A default, non instantiable decorator which only redirects
 * calls to functions to it's decorated object.
 */
public class ActionDecorator implements Action {

    private final Action action;

    /**
     * @param action the decorated action
     */
    protected ActionDecorator(final Action action) {
        this.action = action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTargets(final ActionActor target, final List<ActionActor> targetedParty) {
        action.setTargets(target, targetedParty);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSource(final ActionActor source) {
        action.setSource(source);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ActionActor> getSource() {
        return action.getSource();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionEffect> getEffects() {
        return action.getEffects();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEffects(final List<ActionEffect> effects) {
        action.addEffects(effects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEffect(final ActionEffect effect) {
        action.addEffect(effect);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEffect(final ActionEffect effect) {
        action.removeEffect(effect);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyEffects(final ActionActor target) {
        action.applyEffects(target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getName() {
        return action.getName();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getTargets() {
        return action.getTargets();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getHitChance(final ActionActor target) {
        return action.getHitChance(target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TargetType getTargetType() {
        return action.getTargetType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTargetType(final TargetType targetType) {
        action.setTargetType(targetType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTargetHit() {
        return action.isTargetHit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rollToHit(final ActionActor target) {
        action.rollToHit(target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getValidTargets(final ActionExecutionInstance combatInstance) {
        return action.getValidTargets(combatInstance);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> getTags() {
        return action.getTags();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBaseHitChance() {
        return action.getBaseHitChance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ActionActor> getTarget() {
        return action.getTarget();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLogMessage(final ActionActor target, final boolean success) {
        return action.getLogMessage(target, success);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return action.getDescription();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other) {
        return action.equals(other);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return action.hashCode();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToCurrentHitChance(final double value) {
        action.addToCurrentHitChance(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getCopy() {
        return action.getCopy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEffectsPreview(final ActionActor target) {
        return action.getEffectsPreview(target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTags(final Set<Tag> tags, final boolean arePermanent) {
        action.addTags(tags, arePermanent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTag(final Tag tag, final boolean isPermanent) {
        action.addTag(tag, isPermanent);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeTag(final Tag tag) {
        return action.removeTag(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionCategory getCategory() {
        return action.getCategory();
    }

}
