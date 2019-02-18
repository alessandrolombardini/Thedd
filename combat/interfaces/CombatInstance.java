package combat.interfaces;

import java.util.List;

import combat.enums.CombatStatus;

public interface CombatInstance {
	
	public int getRoundNumber(); 
	
	public void increaseRoundNumber();
	
	public void resetInstance();
		
	public void addNPCsPartyMembers(List<NPCCombatant> hostileNPCs);
	
	public void addPlayerPartyMembers(List<Combatant> alliedPCs);
	
	public void addNPCsPartyMember(NPCCombatant hostileNPC);
	
	public void addPlayerPartyMember(Combatant alliedPC);
	
	public List<NPCCombatant> getNPCsParty();
	
	public List<Combatant> getPlayerParty();

	public void setCombatStatus(CombatStatus newStatus);
	
	public CombatStatus getCombatStatus();

	public List<? extends Combatant> getAllParties();

}
