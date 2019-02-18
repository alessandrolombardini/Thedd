package combat.interfaces;

import java.util.Optional;

public interface ActionActor {
	
	public String getName();
	
	public Optional<Action> getCurrentAction();
	
	public void setAction(Action action);
	
	public void setTarget(ActionActor target);
	
	public int compareTo(ActionActor other);
	
	public int getPriority();
	
	//public getActions() ?
		
	//setTarget -> modifica bersaglio dell'abilità attuale, fa ricalcolare magnetudine effetto in base al bersaglio
	
	//compareTo, se la source non è un interactable, compara l'agilità dei due personaggi. Se source è un interactable, ha default priorità minore
	//alternativa: ActionActor implementa metodo per ritornare una priorità (interactable ritornerà -1, personaggi la loro statistica, effetti duraturi una loro iniziativa (oppure -1 e gli interactable -2))
	
}
