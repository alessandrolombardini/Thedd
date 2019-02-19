package combat.interfaces;

import java.util.List;

public interface HostileEncounter {

    CombatLogic getCombatLogic(); //getCombatLogic?

    void addNPC(Combatant character);

	void addAll(List<Combatant> characters);

}
