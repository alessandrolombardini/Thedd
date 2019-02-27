package model.environment;

/**
 * Interface that define a factory of rooms.
 * 
 */
public interface RoomFactory {

    /**
     * This methos allows to get a base room that could contains enemies, treasures and contraptions.
     * @return a Room 
     * @throws IllegalStateExeption if rooms are over
     */
    Room createBaseRoom();

    /**
     * This method allows to get a boss room.
     * @return a Room
     * @throws IllegalStateExeption if rooms are over
     */
    Room createBossRoom();

    /**
     * This method allows to get a stairs room.
     * @return a Room
     * @throws IllegalStateExeption if rooms are over
     */
    Room createStairsRoom();
}
