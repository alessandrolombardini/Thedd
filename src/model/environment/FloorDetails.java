package model.environment;

import java.util.Random;

/**
 * This class represents all details of the floor.
 *
 */
public final class FloorDetails {

    private Difficulty difficulty;
    private int numberOfEnemies;
    private int numberOfTreasure;
    private int numberOfContraption;
    private int numberOfRooms;
    private boolean isLastFloor;

    private FloorDetails() {
    }

    /**
     * This method allows to get the number of enemies of the floor.
     * 
     * @return the number of enemies
     */
    public int getNumberOfEnemies() {
        return this.numberOfEnemies;
    }

    /**
     * This method allows to get the number of treasures of the floor.
     * 
     * @return the number of treasures
     */
    public int getNumberOfTreasures() {
        return this.numberOfTreasure;
    }

    /**
     * This method allows to get the number of contraptions of the floor.
     * 
     * @return the number of contraptions
     */
    public int getNumberOfContraptions() {
        return this.numberOfContraption;
    }

    /**
     * This method allows to get the number of rooms of the floor.
     * 
     * @return the number of rooms
     */
    public int getNumberOfRooms() {
        return this.numberOfRooms;
    }

    /**
     * This method allows to get the level of difficulty of the floor.
     * 
     * @return the level of difficulty
     */
    public Difficulty getDifficult() {
        return this.difficulty;
    }

    /**
     * This allows to know if the floor is the last and there is a boss at the end
     * of it.
     * 
     * @return true if the floor is the last
     */
    public boolean isLastFloor() {
        return this.isLastFloor;
    }

    /**
     * This method allows to get a FloorDetails' object with random value.
     * 
     * @param difficulty    of the floor
     * @param numberOfRooms of the floor
     * @param lastFloor     that define if the floor is the last
     * @return a FloorDetails' object
     */
    public static FloorDetails createFloorDetails(final Difficulty difficulty, final int numberOfRooms,
                                                  final boolean lastFloor) {
        final FloorDetails choice = new FloorDetails();
        choice.difficulty = difficulty;
        choice.numberOfRooms = numberOfRooms;
        choice.isLastFloor = lastFloor;
        choice.setRandomValue();
        return choice;
    }

    private void setRandomValue() {
        // TODO Creare un algoritmo più efficace di generazione di numeri casuali
        final Random rand = new Random();
        this.numberOfEnemies = rand.nextInt(this.numberOfRooms);
        this.numberOfContraption = rand.nextInt(this.numberOfRooms);
        this.numberOfTreasure = rand.nextInt(this.numberOfRooms);
    }
}
