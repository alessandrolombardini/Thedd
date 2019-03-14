package model.combat.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import model.combat.action.effect.ActionEffect;
import model.combat.actor.ActionActor;
import model.combat.common.CombatInstance;
import model.combat.tag.Tag;

/**
 * Basic implementation of an Action.
 */
public class ActionImpl implements Action {
    private final List<ActionActor> targets = new ArrayList<ActionActor>();
    private Optional<ActionActor> source;
    private Optional<ActionActor> currentTarget = Optional.empty();
    private final List<ActionEffect> effects = new ArrayList<>();
    private final Set<Tag> tags = new HashSet<Tag>();
    private String name;
    private int nextTargetIndex;
    private double baseHitChance;
    private TargetType targetType;
    private boolean targetHit = false;
    private String logMessage;
    private String description;
    //manaRequirement? generic getRequirements?

    /**
     * Public constructor.
     * @param name the literal name of the action
     * @param baseHitChance the base hit chance (a number between 0.0 and 1.0)
     * @param targetType what kind of Actor the action can target
     * @param description a description of the action, see {@link #getDescription()}
     * @param logMessage a message for the logger, see {@link #getLogMessage()}
     */
    public ActionImpl(final String name, final double baseHitChance, final TargetType targetType,
            final String description, final String logMessage) {
        this(null, name, Collections.<ActionEffect>emptyList(), baseHitChance, targetType, description, logMessage);
    } 

    /**
     * Public constructor.
     * @param source the source of the action
     * @param name the literal name of the action
     * @param baseHitChance the base hit chance (a number between 0.0 and 1.0)
     * @param targetType what kind of Actor the action can target
     * @param description a description of the action, see {@link #getDescription()}
     * @param logMessage a message for the logger, see {@link #getLogMessage()}
     */
    public ActionImpl(final ActionActor source, final String name, final double baseHitChance, final TargetType targetType,
            final String description, final String logMessage) {
        this(source, name, Collections.<ActionEffect>emptyList(), baseHitChance, targetType, description, logMessage);
    }

    /**
     * Public constructor.
     * @param source the source of the action
     * @param name the literal name of the action
     * @param effects the list of {@link ActionEffect} associated with this action
     * @param baseHitChance the base hit chance (a number between 0.0 and 1.0)
     * @param targetType what kind of Actor the action can target
     * @param description a description of the action, see {@link #getDescription()}
     * @param logMessage a message for the logger, see {@link #getLogMessage()}
     */
    public ActionImpl(final ActionActor source, final String name, final List<ActionEffect> effects, final double baseHitChance, final TargetType targetType,
            final String description, final String logMessage) {
        this.source = Optional.ofNullable(source);
        this.description = description;
        this.logMessage = logMessage;
        this.name = name;
        this.baseHitChance = baseHitChance;
        this.targetType = targetType;
        for (final ActionEffect effect : effects) {
            this.source.ifPresent(effect::updateEffectBySource);
            this.effects.add(effect);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setTargets(final ActionActor target, final List<ActionActor> targetedParty) {
        targets.clear();
        targets.add(Objects.requireNonNull(target));
        nextTargetIndex = 1;
        currentTarget = Optional.ofNullable(targets.get(0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getTargets() {
        return targets;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void selectNextTarget() {
        currentTarget = nextTargetIndex > targets.size() ? Optional.empty() : Optional.ofNullable(targets.get(nextTargetIndex));
        nextTargetIndex++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionActor getCurrentTarget() {
        return currentTarget.get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSource(final ActionActor source) {
        this.source = Optional.of(source);
        effects.forEach(e -> e.updateEffectBySource(source));
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
        if (source.isPresent()) {
            effect.updateEffectBySource(source.get());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionEffect> getEffects() {
        return Collections.unmodifiableList(effects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void applyEffects() {
        if (currentTarget != null) {
            effects.stream().forEach((e) -> {
                e.updateEffectBySource(getSource().get());
                e.updateEffectByTarget(getCurrentTarget());
                e.apply(getCurrentTarget());
            });
        }
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
     * {@inheritDoc}
     */
    @Override
    public double getHitChanceModifier() {
        //Ritorno baseHitChance più tutti i risultati dei vari modificaori correnti della source di tipi
        //??? DefenceModifier, AttackModifier etc..
        //A seconda del caso, obvs (controlla se questa azione è di tipo difensivo, offensivo o che so altro
        return baseHitChance;
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
        if (!(other instanceof ActionImpl)) {
            return false;
        } else {
            final ActionImpl o = ((ActionImpl) other);
            return  Objects.equals(getName(), o.getName())
                    && Objects.equals(getDescription(), o.getDescription())
                    && Objects.equals(getLogMessage(), o.getLogMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getLogMessage(), getDescription());
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
    public List<ActionActor> getValidTargets(final CombatInstance combatInstance) {
        //Da astrarre
        if (getSource().isPresent()) {
            return Collections.emptyList();
        }
        final ActionActor source = getSource().get();
        switch (getTargetType()) {
        case ALLY:
            return combatInstance.getNPCsParty().contains(source) ? combatInstance.getPlayerParty() 
                    : combatInstance.getNPCsParty();
        case EVERYONE:
            return combatInstance.getAllParties();
        case FOE:
            return combatInstance.getPlayerParty().contains(source) ? combatInstance.getNPCsParty() 
                    : combatInstance.getPlayerParty();
        case SELF:
            return Collections.singletonList((ActionActor) source);
        default:
            throw new IllegalStateException("Target type of the action was not found");
        }
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
    public void addTag(final Tag tag) {
        tags.add(tag);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addTags(final Set<Tag> tags) {
        tags.addAll(tags);
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
    public double getBaseHitChance() {
        return baseHitChance;
    }

    @Override
    public void rollToHit() {
      //DA ASTRARRE
        final ActionActor target = getCurrentTarget();
        if (target == null) {
            targetHit = false;
        } else {
            //offensiveStrategy/defensiveStrategy, collezioni di ActionHitStrategy (implementazioni: reflexStrategy, willpowerStrategy etc...)
            final Random dice = new Random(); //classe utils.DiceRoller potrebbe essere comoda?
            double sourceModifier, actionModifier, targetModifier, sourceStatusesModifiers, targetStatusesModifier;

            actionModifier = getHitChanceModifier();

            if (getTargetType() != TargetType.ALLY && getTargetType() != TargetType.SELF) { 
                /*
                 * sourceChance = currentActor.getPriority() (e getPriority richiamerà getCurrentReflexes a sto punto)
                 * modificare poi baseChance in modo da decidersi
                 */
                //if target.getClass() == ActionActor.class -> targetModifier = target.getReflexes etc... -> anche il check è da spostare nella strategy
                //if (target.getAction() instanceof AbstractDefensiveAction) {
                //defenceModifier = target.getAction().getModifier()
                //}
                //successThreshold = tutti i modificatori sopra combinati in qualche modo (addizione probs)
                final double successThreshold = actionModifier;
                targetHit =  dice.nextDouble() < successThreshold;
            } else {
                targetHit =  true;
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getLogMessage() {
        return logMessage == null ? "[Action log message missing]" : logMessage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description == null ? "[Action description missing]" : description;
    }

}
