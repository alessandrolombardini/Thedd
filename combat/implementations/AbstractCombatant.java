package combat.implementations;

import java.util.Optional;

import combat.interfaces.Action;
import combat.interfaces.ActionActor;
import combat.interfaces.CombatInstance;
import combat.interfaces.Combatant;

public abstract class AbstractCombatant implements Combatant {
	
	private final String name;
	private Optional<Action> currentAction;
	private CombatInstance combatInstance;
	
	
	public AbstractCombatant(String name) {	
		this.name = name;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Optional<Action> getCurrentAction() {
		return currentAction;
	}

	@Override
	public void setAction(Action action) {
		currentAction = Optional.ofNullable(action);
	}

	@Override
	public void setTarget(ActionActor target) {
		if(!getCurrentAction().equals(Optional.empty())) {
			getCurrentAction().get().setTarget(target);
		}
	}

	@Override
	public int compareTo(ActionActor other) {
		return this.getPriority() - other.getPriority();
	}

	@Override
	public int getPriority() {
		return 1; ///DA CAMBIARE: RIFERISCITI AL PERSONAGGIO
	}
	

	@Override
	public void setCombatInstance(CombatInstance instance) {
		this.combatInstance = instance;
	}

	@Override
	public void getCharacter() {
		//DA CAMBIARE ANCHE NELL'INTERFACCIA
	} 
	
	protected CombatInstance getInstance() {
		return combatInstance;
	}

}
