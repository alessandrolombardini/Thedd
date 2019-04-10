package thedd.model.combat.instance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import thedd.model.character.BasicCharacter;
import thedd.model.combat.actor.ActionActor;

/**
 *  Basic implementation of the {@link ActionExecutionInstance} interface.
 */
public class ExecutionInstanceImpl implements ActionExecutionInstance {

    private final List<ActionActor> npcsParty = new ArrayList<>();
    private final List<ActionActor> playerParty = new ArrayList<>();
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
    public void addNPCsPartyMembers(final List<ActionActor> hostileNPCs) {
        npcsParty.addAll(hostileNPCs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPlayerPartyMembers(final List<ActionActor> alliedPCs) {
        playerParty.addAll(alliedPCs);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNPCsPartyMember(final ActionActor hostileNPC) {
        npcsParty.add(hostileNPC);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addPlayerPartyMember(final ActionActor alliedPC) {
        playerParty.add(alliedPC);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getNPCsParty() {
        return Collections.unmodifiableList(npcsParty);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getPlayerParty() {
        return Collections.unmodifiableList(playerParty);
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
        return Stream.concat(playerParty.stream(), npcsParty.stream()).collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionExecutionInstance getCopy() {
        final ActionExecutionInstance copy = new ExecutionInstanceImpl();
        copy.addPlayerPartyMembers(getPlayerParty());
        copy.addNPCsPartyMembers(getNPCsParty());
        copy.setCombatStatus(getCombatStatus());
        while (getRoundNumber() > copy.getRoundNumber()) {
            copy.increaseRoundNumber();
        }
        return copy;
    }

    @Override
    public long getNumberOfAliveCharacters(List<ActionActor> actors) {
        return  actors.stream()
                      .filter(a -> a instanceof BasicCharacter)
                      .map(a -> ((BasicCharacter) a))
                      .filter(BasicCharacter::isAlive)
                      .count();
    }
}
