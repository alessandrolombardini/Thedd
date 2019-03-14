package model.combat.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.combat.actor.ActionActor;

/**
 *  Basic implementation of the CombatInstance interface.
 */
public class CombatInstanceImpl implements CombatInstance {

    private final List<ActionActor> hostiles = new ArrayList<>();
    private final List<ActionActor> friendlies = new ArrayList<>();
    private int roundCount;
    private CombatStatus combatStatus = CombatStatus.NOT_STARTED;

    /**
     * {@inheritDoc}
     */
    @Override
    public int getRoundNumber() {
        return roundCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increaseRoundNumber() {
        roundCount++;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetInstance() {
        roundCount = 0;
        hostiles.clear();
        friendlies.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNPCsPartyMembers(final List<ActionActor> hostileNPCs) {
        hostiles.addAll(hostileNPCs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPlayerPartyMembers(final List<ActionActor> alliedPCs) {
        friendlies.addAll(alliedPCs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNPCsPartyMember(final ActionActor hostileNPC) {
        hostiles.add(hostileNPC);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPlayerPartyMember(final ActionActor alliedPC) {
        friendlies.add(alliedPC);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getNPCsParty() {
        return Collections.unmodifiableList(hostiles);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getPlayerParty() {
        return Collections.unmodifiableList(friendlies);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCombatStatus(final CombatStatus newStatus) {
        combatStatus = newStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CombatStatus getCombatStatus() {
        return combatStatus;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getAllParties() {
        return Stream.concat(friendlies.stream(), hostiles.stream()).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CombatInstance getCopy() {
        final CombatInstance copy = new CombatInstanceImpl();
        copy.addPlayerPartyMembers(getPlayerParty());
        copy.addNPCsPartyMembers(getNPCsParty());
        copy.setCombatStatus(getCombatStatus());
        while (getRoundNumber() > copy.getRoundNumber()) {
            copy.increaseRoundNumber();
        }
        return copy;
    }
}
