package combat.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import combat.enums.CombatStatus;
import combat.interfaces.ActionActor;
import combat.interfaces.CombatInstance;

public class CombatInstanceImpl implements CombatInstance {

    private final List<ActionActor> hostiles = new ArrayList<>();
    private final List<ActionActor> friendlies = new ArrayList<>();
    private int roundCount;
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
    public void addNPCsPartyMembers(final List<ActionActor> hostileNPCs) {
        hostiles.addAll(hostileNPCs);
    }
    @Override
    public void addPlayerPartyMembers(final List<ActionActor> alliedPCs) {
        friendlies.addAll(alliedPCs);
    }
    @Override
    public void addNPCsPartyMember(final ActionActor hostileNPC) {
        hostiles.add(hostileNPC);
    }
    @Override
    public void addPlayerPartyMember(final ActionActor alliedPC) {
        friendlies.add(alliedPC);
    }
    @Override
    public List<ActionActor> getNPCsParty() {
        return Collections.unmodifiableList(hostiles);
    }
    @Override
    public List<ActionActor> getPlayerParty() {
        return Collections.unmodifiableList(friendlies);
    }
    @Override
    public void setCombatStatus(final CombatStatus newStatus) {
        combatStatus = newStatus;
    }
    @Override
    public CombatStatus getCombatStatus() {
        return combatStatus;
    }
    @Override
    public List<ActionActor> getAllParties() {
        return Stream.concat(friendlies.stream(), hostiles.stream()).collect(Collectors.toList());
    }

}
