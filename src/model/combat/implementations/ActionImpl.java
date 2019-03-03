package model.combat.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import model.combat.enums.TargetType;
import model.combat.interfaces.Action;
import model.combat.interfaces.ActionActor;
import model.combat.interfaces.ActionEffect;
import model.combat.interfaces.CombatInstance;
import model.combat.interfaces.Combatant;


public class ActionImpl implements Action {
    private final List<ActionActor> targets = new ArrayList<ActionActor>();
    private Optional<ActionActor> source;
    private final List<ActionEffect> effects = new ArrayList<>();
    private String name;
    private double baseHitChance;
    private TargetType targetType;
    //gettype
    //manaRequirement? oppure un generico getRequirements?

    public ActionImpl(final String name, final double baseHitChance, final TargetType targetType) {
        this(null, name, Collections.<ActionEffect>emptyList(), baseHitChance, targetType);
    } 

    public ActionImpl(final ActionActor source, final String name, final double baseHitChance, final TargetType targetType) {
        this(source, name, Collections.<ActionEffect>emptyList(), baseHitChance, targetType);
    }

    public ActionImpl(final ActionActor source, final String name, final List<ActionEffect> effects, final double baseHitChance, final TargetType targetType) {
        this.source = Optional.ofNullable(source);
        this.name = name;
        this.baseHitChance = baseHitChance;
        this.targetType = targetType;
        for (final ActionEffect effect : effects) {
            this.source.ifPresent(effect::updateEffectBySource);
            this.effects.add(effect);
        }
    }

    @Override
    public void setTargets(final ActionActor target, final List<ActionActor> targetedParty) {
        targets.clear();
        targets.add(Objects.requireNonNull(target));
    }

    @Override
    public List<ActionActor> getTargets() {
        return targets;
    }

    @Override
    public void setSource(final ActionActor source) {
        this.source = Optional.of(source);
        effects.forEach(e -> e.updateEffectBySource(source));
    }

    @Override
    public void addEffects(final List<ActionEffect> effects) {
        for (final ActionEffect effect : effects) {
            addEffect(effect);
        }
    }

    @Override
    public void addEffect(final ActionEffect effect) {
        effects.add(Objects.requireNonNull(effect));
        if (source.isPresent()) {
            effect.updateEffectBySource(source.get());
        }
    }

    @Override
    public List<ActionEffect> getEffects() {
        return Collections.unmodifiableList(effects);
    }

    @Override
    public void applyEffects(final ActionActor target) {
        effects.stream().forEach((e) -> {
                e.updateEffectBySource(getSource().get());
                e.updateEffectByTarget(target);
                e.apply(target);
            });
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Optional<ActionActor> getSource() {
        return source;
    }

    @Override
    public double getHitChanceModifier() {
        //Ritorno baseHitChance più tutti i risultati dei vari modificaori correnti della source di tipi
        //??? DefenceModifier, AttackModifier etc..
        //A seconda del caso, obvs (controlla se questa azione è di tipo difensivo, offensivo o che so altro
        return baseHitChance;
    }

    @Override
    public void setTargetType(final TargetType targetType) {
        this.targetType = targetType;
    }

    @Override
    public TargetType getTargetType() {
        return targetType;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ActionImpl)) {
            return false;
        } else {
            final ActionImpl o = ((ActionImpl) other);
            //Considerare anche se gli effetti e i bersagli sono uguali?
            return getSource().equals(o.getSource())
                    && getName().equals(o.getName())
                    && getTargetType() == o.getTargetType();
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(/*getSource(), */getName(), getTargetType());
    }

    @Override
    public void removeEffect(final ActionEffect effect) {
        effects.remove(effect);
    }

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
            return Collections.singletonList((Combatant) source);
        default:
            throw new IllegalStateException("Target type of the action was not found");
        }
    }

    @Override
    public boolean isTargetHit(final ActionActor target) {

        //DA ASTRARRE
        final Random dice = new Random(); //classe utils.DiceRoller potrebbe essere comoda?
        double sourceModifier, actionModifier, targetModifier, sourceStatusesModifiers, targetStatusesModifier;

        actionModifier = getHitChanceModifier();

        if (getTargetType() != TargetType.ALLY && getTargetType() != TargetType.SELF) { 
            /*
             * sourceChance = currentActor.getPriority() (e getPriority richiamerà getCurrentReflexes a sto punto)
             * modificare poi baseChance in modo da decidersi
             */
            //if target.getClass() == ActionActor.class -> targetModifier = target.getReflexes etc...
            //if (target.getAction() instanceof AbstractDefensiveAction) {
                    //defenceModifier = target.getAction().getModifier()
            //}
            //successThreshold = tutti i modificatori sopra combinati in qualche modo (addizione probs)
            final double successThreshold = actionModifier;
            return dice.nextDouble() < successThreshold;
        } else {
            return true;
        }
    }


}
