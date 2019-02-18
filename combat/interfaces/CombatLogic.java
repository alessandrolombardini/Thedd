package combat.interfaces;

import java.util.List;

import combat.enums.CombatStatus;

public interface CombatLogic {
	
	public void setCombatInstance(CombatInstance newInstance);
	
	public void setPlayerPendingAction(Action action);
	
	public void addPlayerPendingActionTarget(ActionActor target);
	
	public CombatStatus getCombatStatus();
	
	public ActionResult executeNextAction();
	//NOTA: Probabilmente il Controller avrà bisogno di eseguire ogni azione manualmente, perchè deve leggere
	//il risultato dell'azione e, se opportuno, far partire le animazioni 
	
	public void resetCombat();
	
	public void startCombat();
	
	public void prepareNextRound();

	public void addActorToQueue(ActionActor action);
	
	public boolean isRoundReady();
	
	public List<ActionActor> getValidTargets(Action action);
}
