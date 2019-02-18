package combat.interfaces;

import java.util.List;

import combat.enums.CombatStatus;

public interface CombatInstance {
	
	public int getRoundNumber(); 
	
	public void increaseRoundNumber();
	
	public void resetInstance();
		
	public void addNPCsPartyMembers(List<ActionActor> hostileNPCs);
	
	public void addPlayerPartyMembers(List<ActionActor> alliedPCs);
	
	public void addNPCsPartyMember(ActionActor hostileNPC);
	
	public void addPlayerPartyMember(ActionActor alliedPC);
	
	public List<ActionActor> getNPCsParty();
	
	public List<ActionActor> getPlayerParty();

	public void setCombatStatus(CombatStatus newStatus);
	
	public CombatStatus getCombatStatus();

	public List<ActionActor> getAllParties();

}
