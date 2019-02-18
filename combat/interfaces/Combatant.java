package combat.interfaces;

public interface Combatant extends ActionActor {
	
	public void setCombatInstance(CombatInstance instance); //Probabilmente serve solo agli NPC
	
	public void getCharacter(); //DA CAMBIARE
	
	//lista di attributi (quantificati e semplici) che influenzeranno gli ActionEffects
	
}
