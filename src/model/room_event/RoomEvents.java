package model.room_event;

/**
 * Class to create RoomEvents on demand.
 *
 */
public class RoomEvents {

    private RoomEvents() {

    }

    /**
     * 
     * @return
     *  an instance of {@link model.room_event.Stairs}.
     */
    public static final RoomEvent getStairs() {
        return new Stairs();
    }
    /**
     * 
     * @return
     *  an instance of {@link model.room_event.Combat}.
     */
    public static final RoomEvent getCombat() {
        return null;
    }
    /**
     * 
     * @return
     *  an instance of {@link model.room_event.TreasureChest}.
     */
    public static final RoomEvent getTreasureChest() {
        return new TreasureChest();
    }
    /**
     * 
     * @return
     *  an instance of {@link model.room_event.Contraption}.
     */
    public static final RoomEvent getContraption() {
        return null;
    }
}
