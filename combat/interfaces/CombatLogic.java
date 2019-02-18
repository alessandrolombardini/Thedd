package combat.interfaces;

import combat.enums.CombatStatus;

public interface CombatLogic {
	
	public void setCombatInstance(CombatInstance newInstance);
	
	public void setPlayerPendingAction(Action action);
	
	public void addPlayerPendingActionTarget(ActionActor target);
	
	public CombatStatus getCombatStatus(); //-> restituisce PLAYER_WON PLAYER_LOST NOT_ENDED
	
	public ActionResult executeNextAction();
	//NOTA: Probabilmente il Controller avrà bisogno di eseguire ogni azione manualmente, perchè deve leggere
	//il risultato dell'azione e, se opportuno, far partire le animazioni QUINDI
	
	
	//il controller chiama executeNextAction sulla logica del combattimento corrente e ne ricava un ActionResult (BRUTTINO)
	
	
	public void resetCombat();  //maybe builds a CombatInstance?
	
	public void startCombat();
	
	public void prepareNextRound();

	public void addActorToQueue(ActionActor action);
	
	public boolean isRoundReady();
}
