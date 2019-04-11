package thedd.model.world.room;

/**
 * Interface that define a factory of rooms.
 */
public interface RoomFactory {

    /**
     * This method allows to get a random room that respect what should be inside
     * the current floor.
     * 
     * @return a Room
     * @throws IllegalStateExeption if rooms are over
     */
    Room createRoom();
}
