package combat.interfaces;

import java.util.List;

import combat.enums.TargetType;
import combat.interfaces.ActionActor;

public interface Action {
	
	//anche metodi per rimuovere gli effetti magari
	
	//public boolean checkRequirements()
	
	public void setTargets(ActionActor target, List<? extends Combatant> targetedParty);
	
	public void setSource(ActionActor source); //ActionActor può esserlo anche un interagibile
	
	public ActionActor getSource();
	
	public List<ActionEffect> getEffects();
	
	public void addEffects(List<ActionEffect> effects);
	
	public void addEffect(ActionEffect effect);
	
	public void applyEffects(ActionActor target);
	
	public String getName();
	
	public String getLogMessage();  //Probabilmente dovrebbe essere l'effetto ad avere il log 
	
	public List<ActionActor> getTargets();
	
	public double getHitChanceModifier();
	
	public TargetType getTargetType();
	
	public void setTargetType(TargetType targetType);
	
}
