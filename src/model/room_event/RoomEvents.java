package model.room_event;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Class to create RoomEvents on demand.
 *
 */
public class RoomEvents {

    private static final List<Class<? extends Contraption>> CONTRAPTION_DATABASE = new ArrayList<>();
    private static Random r = new Random();
    
    
    static {
        CONTRAPTION_DATABASE.add(ContraptionTrap.class);
    }
    
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
            try {
                return CONTRAPTION_DATABASE.get(r.nextInt(CONTRAPTION_DATABASE.size())).newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
    }
}
