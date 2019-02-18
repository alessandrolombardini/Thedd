package combat.interfaces;

import java.util.List;

public interface HostileEncounter {
	
	public CombatLogic getCombatLogic(); //getCombatLogic?
	
	public void addNPC(Combatant character);
	
	public void addAll(List<Combatant> characters);

}
