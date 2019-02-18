package combat.interfaces;

public interface CombatController {
	
	public void characterSelected(Combatant character);
	
	public void handleCombatRound();
	
	public void startNewCombat(HostileEncounter encounter);
	
	/* public void characterSelected(Character character) {
	 * 
	 * 	check if character selected is friendly or foe (select character or select target) 
	 * 	if(character is NPCCombatant) {
	 * 		currentCharacter = session.getCurrentCharacter(); 		
	 * 		((ActionActor)currentCharacter).getAction().setTargets(character);		
	 * 		
	 * 		Ci pensa setTargets a fissarli tutti, no scelta a giocatore per adesso e wabbuò		
	 * 		logic.addToQueue(session.getCurrentCharacter())
	 * 		if(logic.roundIsReady() || logic.getRoundStatus == ROUND_IN_PROGRESS) {
	 * 			handleCombatRound();
	 * 		}
	 * 	}
	 * }
	*/
	
	/* public void handleCombatRound() {
	 * 		if(logic.getRoundStatus != ROUND_IN_PROGRESS) {
	 * 			logic.getInstance().increaseRoundNumber();
	 * 		}
	 * 		while(logic.getRoundStatus == ROUND_IN_PROGRESS) {
	 * 			result = logic.executeNextAction();
	 * 			message = messageLogic.getNewMessage(result);
	 * 			pathManager.buildPath(result.getSource()); (in base al tipo di result.getResult()-> actor.getPath() + (actor.getAction().getPath() oppure un path definit in base a getResult (parry/dodge/etc...) ) //DEFINIRE MEGLIO 
	 * 			view.updateCharacterView(result, path); ***** 
	 * 			view.updateCharacterView(result.target(), path); //Da modificare perchè result ora ha una lista di results (ricorda poi che personaggio dopo un colpo può morire)
	 *		  	string = combatLogget.Log(result)
	 *  		view.UpdateLogger(string)
	 *  		logic.checkCombatEnd();  
	 * 		}
	 * 		if(logic.getRoundStatus == PLAYER_WON) {
	 * 
	 *  	} else if (logic.getRoundStatus == PLAYER_LOST) {
	 *  
	 *  	}
	 *  
	 *  *****Il metodo setaccia il result fornito e decide cosa fare di conseguenza (NON SI SALVA DATI) -> magari si può utilizzare mapper/converter? bah
	*/
	
	
	/* public void startNewCombat() {
	 * 		create instance
	 * 		set combatLog
	 * 		set combatMessageManager
	 * 		signal view -> view.startCombat()\view.showActions(session.currentCharacter);
	 * }
	 */
}
