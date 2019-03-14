package model.combat.modifier;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import model.combat.common.Modifiable;
import model.combat.tag.Tag;

/**
 * Abstract implementations of Modifiers' basic behavior.
 */
public abstract class AbstractModifier implements Modifier {

    private final Set<Tag> modifiableTargetTags = new HashSet<>();
    private final Set<Tag> targetActorTargetTags = new HashSet<>();
    private final Set<Tag> sourceActorTargetTags = new HashSet<>();
    private final Set<Tag> modifiableUnallowedTags = new HashSet<>();
    private final Set<Tag> targetActorUnallowedTags = new HashSet<>();
    private final Set<Tag> sourceActorUnallowedTags = new HashSet<>();
    private boolean percentage;
    private double value;
    private ModifierActivation type;

    /**
     * Constructor for the abstract class.
     * @param value the value that will be applied to the modifiable
     * @param isPercentage true if the value shall be treated as a percentage
     * @param type declares whether this modifier should be applied on attack, defense or everytime
     */
    protected AbstractModifier(final double value, final boolean isPercentage, final ModifierActivation type) {
        this.value = value;
        this.percentage = isPercentage;
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(final double value) {
        this.value = value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setIsPercentage(final boolean isPercentage) {
        this.percentage = isPercentage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isPercentage() {
        return percentage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ModifierActivation getModifierActivation() {
        return type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setModifierActivation(final ModifierActivation type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addModifiableTargetTag(final Tag tag, final boolean isAllowed) {
        Set<Tag> targetSet = isAllowed ? modifiableTargetTags : modifiableUnallowedTags;
        targetSet.add(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTargetActorTargetTag(final Tag tag, final boolean isAllowed) {
        Set<Tag> targetSet = isAllowed ? targetActorTargetTags : targetActorUnallowedTags;
        targetSet.add(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addSourceActorTargetTag(final Tag tag, final boolean isAllowed) {
        Set<Tag> targetSet = isAllowed ? sourceActorTargetTags : sourceActorTargetTags;
        targetSet.add(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeModifiableTargetTag(final Tag tag, final boolean isAllowed) {
        Set<Tag> targetSet = isAllowed ? modifiableTargetTags : modifiableUnallowedTags;
        return targetSet.remove(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeTargetActorTargetTag(final Tag tag, final boolean isAllowed) {
        Set<Tag> targetSet = isAllowed ? targetActorTargetTags : targetActorUnallowedTags;
        return targetSet.remove(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean removeSourceActorTargetTag(final Tag tag, final boolean isAllowed) {
        Set<Tag> targetSet = isAllowed ? sourceActorTargetTags : sourceActorUnallowedTags;
        return targetSet.remove(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> getModifiableTargetTags(final boolean allowed) {
        Set<Tag> targetSet = allowed ? modifiableTargetTags : modifiableUnallowedTags;
        return Collections.unmodifiableSet(targetSet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> getTargetActorTargetTags(final boolean allowed) {
        Set<Tag> targetSet = allowed ? targetActorTargetTags : targetActorUnallowedTags;
        return Collections.unmodifiableSet(targetSet);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<Tag> getSourceActorTargetTags(final boolean allowed) {
        Set<Tag> targetSet = allowed ? sourceActorTargetTags : sourceActorUnallowedTags;
        return Collections.unmodifiableSet(targetSet);
    }

    @Override
    public abstract boolean accept(Modifiable modifiable);

    @Override
    public abstract void modify(Modifiable modifiable);

}
