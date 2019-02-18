package combat.interfaces;

import java.util.List;

public interface NPCCombatant extends Combatant {
	
	public void setAIActions(List<NPCAction> actions);
	
	public void setNextAIAction();
	
	public void setNextAIAction(ActionActor target);
	
}
