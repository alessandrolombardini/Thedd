package combat.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import combat.enums.CombatStatus;
import combat.interfaces.CombatInstance;
import combat.interfaces.Combatant;
import combat.interfaces.NPCCombatant;

public class CombatInstanceImpl implements CombatInstance {
	
	private List<NPCCombatant> hostiles = new ArrayList<NPCCombatant>();
	private List<Combatant> friendlies = new ArrayList<Combatant>();
	private int roundCount = 0;
	private CombatStatus combatStatus = CombatStatus.NOT_STARTED;
	
	@Override
	public int getRoundNumber() {
		return roundCount;
	}
	@Override
	public void increaseRoundNumber() {
		roundCount++;
	}
	@Override
	public void resetInstance() {
		roundCount = 0;
		hostiles.clear();
		friendlies.clear();
	}
	
	@Override
	public void addNPCsPartyMembers(List<NPCCombatant> hostileNPCs) {
		hostiles.addAll(hostileNPCs);
	}
	@Override
	public void addPlayerPartyMembers(List<Combatant> alliedPCs) {
		friendlies.addAll(alliedPCs);
	}
	@Override
	public void addNPCsPartyMember(NPCCombatant hostileNPC) {
		hostiles.add(hostileNPC);
	}
	@Override
	public void addPlayerPartyMember(Combatant alliedPC) {
		friendlies.add(alliedPC);
	}
	@Override
	public List<NPCCombatant> getNPCsParty() {
		return Collections.unmodifiableList(hostiles);
	}
	@Override
	public List<Combatant> getPlayerParty() {
		return Collections.unmodifiableList(friendlies);
	}
	@Override
	public void setCombatStatus(CombatStatus newStatus) {
		combatStatus = newStatus;
	}
	@Override
	public CombatStatus getCombatStatus() {
		return combatStatus;
	}

}
