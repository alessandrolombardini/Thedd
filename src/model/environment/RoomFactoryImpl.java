package model.environment;

/**
 * Implementation of {@link model.environment.RoomFactory}.
 * 
 */
public class RoomFactoryImpl implements RoomFactory {

    private final FloorDetails floorChoice;
    private int remainingEnemies;
    private int remainingTreasure;
    private int remainingContraption;
    private int roomIndex;

    /**
     * RoomFactoryImpl constructor.
     * 
     * @param floorChoice that describe the floor
     */
    public RoomFactoryImpl(final FloorDetails floorChoice) {
        this.floorChoice = floorChoice;
        this.remainingContraption = this.floorChoice.getNumberOfContraptions();
        this.remainingTreasure = this.floorChoice.getNumberOfTreasures();
        this.remainingEnemies = this.floorChoice.getNumberOfEnemies();
        this.roomIndex = -1;
    }

    @Override
    public final Room createRoom() {
        if (this.roomIndex >= this.floorChoice.getNumberOfRooms()) {
            throw new IllegalStateException();
        }
        this.roomIndex++;
        return this.selectRoom();
    }

    private Room selectRoom() {
        if (this.floorChoice.isLastFloor() && this.roomIndex == (this.floorChoice.getNumberOfRooms() - 1)) {
            return this.createBossRoom();
        } else if (this.roomIndex == (this.floorChoice.getNumberOfRooms() - 1)) {
            return this.createStairsRoom();
        } else {
            return this.createBaseRoom();
        }
    }

    private Room createBossRoom() {
        return null;
    }

    private Room createStairsRoom() {
        return null;
    }

    private Room createBaseRoom() {
        return null;
    }

}
