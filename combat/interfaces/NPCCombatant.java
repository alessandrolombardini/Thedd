package combat.interfaces;

import combat.implementations.AutomaticActionActor;

public interface NPCCombatant extends Combatant, AutomaticActionActor {
	
	public void setNextAIAction();
	
}
