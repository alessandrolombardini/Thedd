package combat.interfaces;

public interface NPCCombatant extends Combatant {
	
	public void setNextAIAction();
	
	public void setNextAIAction(ActionActor target);
	
}
