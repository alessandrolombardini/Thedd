package model.room_event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

/**
 * Class to create RoomEvents on demand.
 *
 */
public final class RoomEvents {

    private static final List<Supplier<Contraption>> CONTRAPTION_DATABASE = new ArrayList<>();
    private static final Random RNG = new Random();

    static {
        CONTRAPTION_DATABASE.add(ContraptionTrap::newInstance);
        CONTRAPTION_DATABASE.add(ContraptionSanctuary::newInstance);
    }

    private RoomEvents() {
    }

    /**
     * 
     * @return
     *  an instance of {@link model.room_event.Stairs}.
     */
    public static RoomEvent getStairs() {
        return new Stairs();
    }
    /**
     * 
     * @return
     *  an instance of {@link model.room_event.CombatEvent}.
     */
    public static RoomEvent getCombat() {
        return new CombatEventImpl();
    }
    /**
     * 
     * @return
     *  an instance of {@link model.room_event.TreasureChest}.
     */
    public static RoomEvent getTreasureChest() {
        return TreasureChest.newInstance();
    }
    /**
     * 
     * @return
     *  an instance of {@link model.room_event.InteractableActionPerformer}.
     */
    public static RoomEvent getContraption() {
            return CONTRAPTION_DATABASE.get(RNG.nextInt(CONTRAPTION_DATABASE.size())).get();
    }
}
