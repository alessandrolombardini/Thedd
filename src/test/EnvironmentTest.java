package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import thedd.model.roomevent.combatevent.CombatEvent;
import thedd.model.world.Difficulty;
import thedd.model.world.environment.Environment;
import thedd.model.world.environment.EnvironmentImpl;
import thedd.model.world.floor.Floor;
import thedd.model.world.floor.FloorDetails;
import thedd.model.world.floor.FloorDetailsFactory;
import thedd.model.world.floor.FloorDetailsFactoryImpl;
import thedd.model.world.room.Room;
import thedd.model.world.room.RoomFactoryImpl;

/**
 * This class allows to test combat module.
 */
public class EnvironmentTest {

    private final int numberOfTest = 10000;

    /**
     * Test of new environment.
     */
    @Test
    public void testCreateEnvironement() {
        final int numberOfRooms = 3;
        final int numberOfFloors = 4;

        final Environment environment = new EnvironmentImpl(numberOfFloors, numberOfRooms);
        assertEquals(environment.getCurrentFloorIndex(), 0);

        final Floor floor = environment.getCurrentFloor();
        assertEquals(floor.getCurrentRoomIndex(), 0);
        assertFalse(floor.checkToChangeFloor());
        assertTrue(floor.hasNextRoom());

        final Room room = floor.getCurrentRoom();
        assertFalse(room.checkToMoveOn());
    }

    /**
     * Test of floor options.
     */
    @Test
    public void testFloorOptionsWithEasyMode() {
        this.testWithDifferentDifficulty(Difficulty.EASY, true);
        this.testWithDifferentDifficulty(Difficulty.NORMAL, true);
        this.testWithDifferentDifficulty(Difficulty.HARD, true);
        this.testWithDifferentDifficulty(Difficulty.EASY, false);
        this.testWithDifferentDifficulty(Difficulty.NORMAL, false);
        this.testWithDifferentDifficulty(Difficulty.HARD, false);
    }

    private void testWithDifferentDifficulty(final Difficulty difficulty, final boolean boss) {
        final FloorDetailsFactory factory = new FloorDetailsFactoryImpl();
        final int roomForBoss = boss ? 1 : 0;
        for (int i = 1; i < numberOfTest; i++) {
            final FloorDetails details = factory.createFloorDetails(difficulty, i, boss);
            assertEquals(details.isBossFloor(), boss);
            assertTrue(details.getNumberOfEnemies() <= RoomFactoryImpl.MAX_ENEMIES_PER_ROOM * (i - roomForBoss));
            assertTrue(details.getNumberOfEnemies() >= RoomFactoryImpl.MIN_ENEMIES_PER_ROOM * (i - roomForBoss));
            assertEquals(details.getNumberOfRooms(), i);
            assertTrue(details.getNumberOfContraptions() + details.getNumberOfTreasures() 
                            >= RoomFactoryImpl.MIN_INTERACTABLE_ACTION_PER_ROOM * (i - roomForBoss));
            assertTrue(details.getNumberOfContraptions() + details.getNumberOfTreasures() 
                            >= RoomFactoryImpl.MIN_INTERACTABLE_ACTION_PER_ROOM * (i - roomForBoss));

        }
    }

    /** 
     * Test boss room.
     */
    @Test
    public void testBossRoom() {
        final int numberOfRooms = 1;
        final int numberOfFloors = 1;
        final Environment environment = new EnvironmentImpl(numberOfFloors, numberOfRooms);
        final Floor floor = environment.getCurrentFloor();
        final Room room = floor.getCurrentRoom();
        final CombatEvent event = (CombatEvent) room.getEvents().stream().findAny().get();
        assertEquals(event.getHostileEncounter().getNPCs().stream().findAny().get().getName(), "Dark Destructor");
    }
}















