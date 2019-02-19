package combat.interfaces;

import java.util.List;

import combat.enums.CombatStatus;

public interface CombatInstance {

    int getRoundNumber(); 

    void increaseRoundNumber();

    void resetInstance();

    void addNPCsPartyMembers(List<ActionActor> hostileNPCs);

    void addPlayerPartyMembers(List<ActionActor> alliedPCs);

    void addNPCsPartyMember(ActionActor hostileNPC);

    void addPlayerPartyMember(ActionActor alliedPC);

    List<ActionActor> getNPCsParty();

    List<ActionActor> getPlayerParty();

    void setCombatStatus(CombatStatus newStatus);

    CombatStatus getCombatStatus();

    List<ActionActor> getAllParties();

}
