package combat.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import combat.enums.TargetType;
import combat.interfaces.Action;
import combat.interfaces.ActionActor;
import combat.interfaces.ActionEffect;
import combat.interfaces.Combatant;

public abstract class AbstractAction implements Action {
	
	private List<ActionActor> targets = new ArrayList<ActionActor>();
	private ActionActor source;
	private List<ActionEffect> effects = new ArrayList<>();
	private String logMessage;
	private String name;
	private double baseHitChance;
	private TargetType targetType;
	//gettype
	//manaRequirement? oppure un generico getRequirements?
	
	public AbstractAction(ActionActor source, String name, double baseHitChance, TargetType targetType) {
		this(source, name, Collections.<ActionEffect>emptyList(), baseHitChance, targetType);
	}
	
	public AbstractAction(ActionActor source, String name, List<ActionEffect> effects, double baseHitChance, TargetType targetType) {
		this.source = source;
		this.name = name;
		this.baseHitChance = baseHitChance;
		this.targetType = targetType;
		for(ActionEffect effect : effects) {
			effect.updateEffectBySource(source);
			this.effects.add(effect);
		}
	}
	
	@Override
	public void setTargets(ActionActor target, List<? extends Combatant> targetedParty) {
		targets.clear();
		targets.add(Objects.requireNonNull(target));
		for(ActionEffect effect : effects) {
			effect.updateEffectByTarget(target);
		}
	}
	
	@Override
	public List<ActionActor> getTargets() {
		return targets;
	}
	
	@Override
	public void setSource(ActionActor source) {	//Perhaps useless as a method
		this.source = source;
	}

	@Override
	public void addEffects(List<ActionEffect> effects) {	//Perhaps useless as a method
		this.effects.addAll(effects);
	}

	@Override
	public void addEffect(ActionEffect effect) {	//Perhaps useless as a method
		effects.add(Objects.requireNonNull(effect));
	}
	
	@Override
	public List<ActionEffect> getEffects() {
		return Collections.unmodifiableList(effects);
	}

	@Override
	public void applyEffects(ActionActor target) {
		//dopo ogni apply devi anche loggare. NOTA:ADESSO SONO GLI EFFETTI CHE LOGGANO (Magari prendendo il nome dell'azione come parametro)
		effects.stream().forEach((e) -> {
				e.updateEffectBySource(getSource());
				e.updateEffectByTarget(target);
				e.apply(target);
			});
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getLogMessage() {	
		return source.getName() + " does a standard action to "; //+ target.getName? ;
	}
	
	@Override
	public ActionActor getSource() {
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
	public void setTargetType(TargetType targetType) {
		this.targetType = targetType;
	}
	
	@Override
	public TargetType getTargetType() {
		return targetType;
	}

}
