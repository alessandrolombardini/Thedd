package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Optional;
import org.junit.Test;
import thedd.model.character.BasicCharacter;
import thedd.model.character.CharacterFactory;
import thedd.model.character.types.EnemyCharacterType;
import thedd.model.combat.action.Action;
import thedd.model.combat.actionexecutor.ActionExecutor;
import thedd.model.combat.actionexecutor.DefaultCombatActionExecutor;
import thedd.model.combat.actionexecutor.OutOfCombatActionExecutor;
import thedd.model.combat.encounter.HostileEncounter;
import thedd.model.combat.encounter.HostileEncounterImpl;
import thedd.model.combat.instance.ActionExecutionInstance;
import thedd.model.combat.instance.CombatStatus;
import thedd.model.combat.instance.ExecutionInstanceImpl;
import thedd.model.world.Difficulty;

/**
 * This class allows to test combat module.
 */
public class CombatTest {

    private static final int NUMBER_OF_ACTORS = 2;
    private static final int NUMBER_OF_NPC = 1;
    private static final int NUMBER_OF_PLAYER = 1;

    private final BasicCharacter player;
    private final ActionExecutionInstance instance;
    private final ActionExecutor logic;
    private final Action action;

    /**
     * Constructor of CombatTest: initialize a standard combat.
     */
    public CombatTest() {
        final HostileEncounter encounter = new HostileEncounterImpl();
        player = CharacterFactory.createPlayerCharacter(Optional.of("Giangeffo"), Difficulty.EASY);
        action = player.getAvailableActionsList().stream().findFirst().get();
        encounter.setCombatLogic(new DefaultCombatActionExecutor());
        encounter.addNPC(CharacterFactory.createEnemy(EnemyCharacterType.GOBLIN, Difficulty.EASY));
        instance = new ExecutionInstanceImpl();
        instance.addPlayerPartyMember(player);
        instance.addNPCsPartyMembers(encounter.getNPCs());
        logic = encounter.getCombatLogic();
        logic.setExecutionInstance(instance);
        logic.startExecutor();
    }

    /**
     * Find out if the instance is all ok with one player and one NPC at the start.
     */
    @Test
    public void testStatusInstanceWithOnePlayerAndOneNPC() {
        assertEquals(instance.getPlayerParty().size(), NUMBER_OF_PLAYER);
        assertEquals(instance.getNPCsParty().size(), NUMBER_OF_NPC);
        assertEquals(instance.getNumberOfAliveCharacters(instance.getAllParties()), NUMBER_OF_ACTORS);
        assertEquals(logic.getExecutionStatus(), CombatStatus.STARTED);
        assertEquals(logic.getOrderedActorsList().size(), NUMBER_OF_ACTORS);
        assertFalse(logic.isRoundReady());
        assertTrue(logic.getOrderedActorsList().contains(player));
    }

    /**
     * Try to set, remove and use actions by the player.
     */
    @Test
    public void testAction() {
        player.addActionToQueue(action, true);
        assertEquals(player.getActionQueue().size(), 1);
        player.removeActionFromQueue(action);
        assertEquals(player.getActionQueue().size(), 0);
        player.addActionToQueue(action, true);
        assertEquals(player.getActionQueue().size(), 1);
        logic.addActorToQueue(player);
        executeNextAction();
    }

    /**
     * Try to use action out of combat.
     */
    @Test
    public void testExecuteActionOutOfCombat() {
        final ActionExecutor logic = new OutOfCombatActionExecutor(action);
        instance.addPlayerPartyMember(player);
        logic.setExecutionInstance(instance);
        executeNextAction();
        assertEquals(logic.getExecutionStatus(), CombatStatus.ROUND_ENDED);
    }

    /**
     * Try to set one player and one enemie and try to make a one hit fight for each side.
     */
    @Test
    public void testCombatWithOnePlayerAction() {
        assertFalse(logic.isRoundReady());
        player.addActionToQueue(action, true);
        logic.addActorToQueue(player);
        assertEquals(player.getActionQueue().size(), 1);
        assertEquals(instance.getRoundNumber(), 1);
        assertTrue(player.isInCombat());
        executeNextAction();
        executeNextAction();
        assertFalse(logic.isRoundReady());
        assertEquals(player.getActionQueue().size(), 0);
        logic.prepareNextRound();
        assertEquals(instance.getRoundNumber(), 2);
    }

    /**
     * Test of execution states.
     */
    @Test
    public void testExecutionStatus() {
        assertEquals(logic.getExecutionStatus(), CombatStatus.STARTED);
        player.addActionToQueue(action, true);
        logic.addActorToQueue(player);
        executeNextAction();
        assertEquals(logic.getExecutionStatus(), CombatStatus.ROUND_IN_PROGRESS);
        executeNextAction();
        assertEquals(logic.getExecutionStatus(), CombatStatus.ROUND_ENDED);
    }



    private void executeNextAction() {
        logic.setNextAction();
        assertTrue(logic.evaluateCurrentAction().isPresent());
        logic.executeCurrentAction();
        logic.updateExecutionStatus();
    }

}
