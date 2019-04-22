package test;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Test;

import thedd.model.character.CharacterFactory;
import thedd.model.character.types.EnemyCharacterType;
import thedd.model.roomevent.RoomEventType;
import thedd.model.roomevent.combatevent.CombatEvent;
import thedd.model.roomevent.combatevent.CombatEventImpl;
import thedd.model.roomevent.floorchanger.FloorChangerEvent;
import thedd.model.roomevent.floorchanger.Stairs;
import thedd.model.roomevent.interactableactionperformer.ContraptionSanctuary;
import thedd.model.roomevent.interactableactionperformer.InteractableActionPerformer;
import thedd.model.world.Difficulty;

/**
 * This class tests thedd.model.roomevent package.
 */
public class RoomEventTest {

    /**
     * Test of InteractableActionPerformer.
     */
    @Test
    public void testCreationInteractableActionPerformer() {
        final InteractableActionPerformer contraption = new ContraptionSanctuary();
        assertFalse(contraption.isCompleted());
        assertTrue(contraption.isSkippable());
        assertEquals(RoomEventType.INTERACTABLE_ACTION_PERFORMER, contraption.getType());
        assertNotNull(contraption.getAvailableActionsList());
    }

    /**
     * Test of FloorChanger.
     */
    @Test
    public void testCreationFloorChanger() {
        final FloorChangerEvent stairs = new Stairs();
        assertEquals(RoomEventType.FLOOR_CHANGER_EVENT, stairs.getType());
        assertFalse(stairs.isCompleted());
        assertTrue(stairs.isConditionMet());
        assertTrue(stairs.isSkippable());
    }

    /**
     * Test of CombatEvent.
     */
    @Test
    public void testCreationCombatEvent() {
        final CombatEvent combat = new CombatEventImpl();
        assertEquals(RoomEventType.COMBAT_EVENT, combat.getType());
        assertNotNull(combat.getHostileEncounter());
        combat.getHostileEncounter().addNPC(CharacterFactory.createFinalBoss(Difficulty.EASY));
        assertNotNull(combat.getHostileEncounter().getNPCs());
//        assertFalse(combat.isCompleted());
        assertFalse(combat.isSkippable());
    }
}
