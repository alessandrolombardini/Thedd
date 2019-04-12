package thedd.model.combat.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.action.targeting.ActionTargeting;
import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.instance.ActionExecutionInstance;
import thedd.model.combat.modifier.HitChanceModifier;
import thedd.model.combat.modifier.Modifier;
import thedd.model.combat.modifier.ModifierActivation;
import thedd.model.combat.tag.Tag;

/**
 * Basic implementation of an Action.
 */
public class ActionImpl implements Action {
    private final List<ActionActor> targets = new ArrayList<ActionActor>();
    private final List<ActionEffect> effects = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<Tag>();
    private final Set<Tag> permanentTags = new HashSet<Tag>();
    private final LogMessageType logMessage;
    private final ActionCategory category; 
    private Optional<ActionActor> source = Optional.empty();
    private Optional<ActionActor> currentTarget = Optional.empty();
    private double currentHitChance;
    private double baseHitChance;
    private boolean targetHit;
    private TargetType targetType;
    private String description;
    private String name;
    private final ActionTargeting targeting;

    /**
     * Public constructor.
     * @param name the literal name of the action
     * @param baseHitChance the base hit chance (a number between 0.0 and 1.0)
     * @param targetType what kind of Actor the action can target
     * @param description a description of the action
     * @param logMessage the category of message for the logger
     * @param category the category of the action
     * @param targeting the targeting system of the action
     */
    public ActionImpl(final String name, final ActionCategory category,
            final ActionTargeting targeting, final double baseHitChance,
            final TargetType targetType, final String description,
            final LogMessageType logMessage) {
        this(null, name, Collections.<ActionEffect>emptyList(), category, targeting, baseHitChance, targetType, description, logMessage);
    } 

    /**
     * Public constructor.
     * @param source the source of the action
     * @param name the literal name of the action
     * @param baseHitChance the base hit chance (a number between 0.0 and 1.0)
     * @param targetType what kind of Actor the action can target
     * @param description a description of the action
     * @param logMessage the category of message for the logger
     * @param category the category of the action
     * @param targeting the targeting system of the action
     */
    public ActionImpl(final ActionActor source, final String name,
            final ActionCategory category, final ActionTargeting targeting,
            final double baseHitChance, final TargetType targetType, 
            final String description, final LogMessageType logMessage) {
        this(source, name, Collections.<ActionEffect>emptyList(), category, targeting, baseHitChance, targetType, description, logMessage);
    }

    /**
     * Public constructor.
     * @param source the source of the action
     * @param name the literal name of the action
     * @param effects the list of {@link ActionEffect} associated with this action
     * @param baseHitChance the base hit chance (a number between 0.0 and 1.0)
     * @param targetType what kind of Actor the action can target
     * @param description a description of the action
     * @param logMessage the category of message for the logger
     * @param category the category of the action
     * @param targeting the targeting system of the action
     */
    public ActionImpl(final ActionActor source, final String name,
            final List<ActionEffect> effects, final ActionCategory category,
            final ActionTargeting targeting,  final double baseHitChance,
            final TargetType targetType, final String description,
            final LogMessageType logMessage) {
        this.targeting = Objects.requireNonNull(targeting);
        this.category = category;
        this.source = Optional.ofNullable(source);
        this.description = description;
        this.name = name;
        this.baseHitChance = baseHitChance;
        this.targetType = targetType;
        this.effects.addAll(effects);
        this.source.ifPresent(s -> effects.forEach(e -> e.setSource(s)));
        this.logMessage = logMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionCategory getCategory() {
        return category;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTargets(final ActionActor target, final List<ActionActor> targetedParty) {
        targets.clear();
        targets.addAll(targeting.getTargets(target, targetedParty));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getTargets() {
        return Collections.unmodifiableList(targets);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<ActionActor> getTarget() {
        return currentTarget;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSource(final ActionActor source) {
        this.source = Optional.of(source);
        effects.forEach(e -> e.setSource(source));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEffects(final List<ActionEffect> effects) {
        for (final ActionEffect effect : effects) {
            addEffect(effect);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addEffect(final ActionEffect effect) {
        effects.add(Objects.requireNonNull(effect));
        source.ifPresent(s -> effects.forEach(e -> e.setSource(s)));
    }

    /**
     * {@inheritDoc}f
     */
    @Override
    public List<ActionEffect> getEffects() {
        return Collections.unmodifiableList(effects);
    }

    /**
     * {@inheritDoc}<p>
     * Tries to apply all the modifiers (excluding the hit
     * chance modifiers) of the source (those which are
     * active on attack) and the target (those which are 
     * active on defense).
     */
    @Override
    public void applyEffects(final ActionActor target) {
        applyModifiers(target, (m) -> !(m instanceof HitChanceModifier));
        if (target != null) {
            effects.stream().forEach((e) -> {
                e.updateEffectBySource(getSource().get());
                e.updateEffectByTarget(target);
                e.apply(target);
            });
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getEffectsPreview(final ActionActor target) {
        final StringBuilder sb = new StringBuilder();
        effects.stream().forEach((e) -> {
            sb.append(e.getPreviewMessage());
            sb.append('\n');
        });
        return sb.toString();
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
    public Optional<ActionActor> getSource() {
        return source;
    }

    /**
     * {@inheritDoc}<p>
     * 
     * Tries to apply all the hit chance modifiers of the 
     * source (those which are active on attack) and the
     * target (those which are active on defense).
     */
    @Override
    public double getHitChance(final ActionActor target) {
        currentTarget =  Optional.ofNullable(target);
        currentHitChance = getBaseHitChance();
        applyModifiers(target, (m) -> m instanceof HitChanceModifier); 
        return currentHitChance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToCurrentHitChance(final double value) {
        currentHitChance += value;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTargetType(final TargetType targetType) {
        this.targetType = targetType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TargetType getTargetType() {
        return targetType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Action)) {
            return false;
        } else {
            final Action o = ((Action) other);
            return  Objects.equals(getName(), o.getName())
                    && getBaseHitChance() == o.getBaseHitChance()
                    && getSource().equals(o.getSource());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBaseHitChance());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeEffect(final ActionEffect effect) {
        effects.remove(effect);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getValidTargets(final ActionExecutionInstance combatInstance) {
        if (!getSource().isPresent()) {
            return Collections.emptyList();
        }
        return targeting.getValidTargets(combatInstance, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isTargetHit() {
        return targetHit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBaseHitChance() {
        return baseHitChance;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void rollToHit(final ActionActor target) {
        if (target == null) {
            targetHit = false;
        } else {
            final Random dice = new Random();
            targetHit = dice.nextDouble() < getHitChance(target);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLogMessage(final ActionActor target, final boolean success) {
        return logMessage.getLogMessage(success, this, target);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description == null ? "[Action description missing]" : description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getCopy() {
        final Action copy = new ActionImpl(getName(), getCategory(), targeting, getBaseHitChance(), getTargetType(), getDescription(), logMessage);
        getEffects().forEach(e -> copy.addEffect(e.getCopy()));
        copy.addTags(tags, false);
        copy.addTags(permanentTags, true);
        source.ifPresent(copy::setSource);
        if (!targets.isEmpty()) {
            final ActionActor originalTarget = targets.get(0);
            copy.setTargets(originalTarget, targets);
        }
        return copy;
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
     * Not implemented, use {@link #setTargets}.
     * @throws UnsupportedOperationException
     */
    @Override
    public void setTarget(final ActionActor target) {
        throw new UnsupportedOperationException("Not supported, use setTargets instead");
    }

    private void applyModifiers(final ActionActor target, final Predicate<Modifier<Action>> filterHitChance) {
        currentTarget =  Optional.ofNullable(target);
        if (source.isPresent()) {
            source.get().getActionModifiers().stream()
                    .filter(filterHitChance::test)
                    .filter(m -> m.getModifierActivation() == ModifierActivation.ACTIVE_ON_ATTACK)
                    .filter(m -> m.accept(this))
                    .forEach(m -> m.modify(this));
        }

        if (currentTarget.isPresent()) {
            currentTarget.get().getActionModifiers().stream()
                    .filter(filterHitChance::test)
                    .filter(m -> m.getModifierActivation() == ModifierActivation.ACTIVE_ON_DEFENCE)
                    .filter(m -> m.accept(this))
                    .forEach(m -> m.modify(this));
        }
    }
}
