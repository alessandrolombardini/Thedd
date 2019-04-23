package thedd.model.world.floor.details;

import thedd.model.world.Difficulty;

/**
 * This class represent the content of a future floor.
 */
public final class FloorDetailsImpl implements FloorDetails {

    private final Difficulty difficulty;
    private final int numberOfRooms;
    private final int numberOfEnemies;
    private final int numberOfTreasure;
    private final int numberOfContraptions;
    private final boolean isLastFloor;

    /**
     * FloorDetials constructor.
     * 
     * @param difficulty of the floor
     * @param numberOfRooms fo the floor
     * @param numberOfEnemies of the floor
     * @param numberOfTreasure of the floor
     * @param numberOfContraptions of the floor
     * @param isLastFloor of the floor
     */
    protected FloorDetailsImpl(final Difficulty difficulty, final int numberOfRooms, final int numberOfEnemies,
                             final int numberOfTreasure, final int numberOfContraptions, 
                             final boolean isLastFloor) {
        this.difficulty = difficulty;
        this.numberOfRooms = numberOfRooms;
        this.numberOfEnemies = numberOfEnemies;
        this.numberOfTreasure = numberOfTreasure;
        this.numberOfContraptions = numberOfContraptions;
        this.isLastFloor = isLastFloor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfRooms() {
        return this.numberOfRooms;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfEnemies() {
        return this.numberOfEnemies;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfTreasures() {
        return this.numberOfTreasure;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNumberOfContraptions() {
        return this.numberOfContraptions;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Difficulty getDifficult() {
        return this.difficulty;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isBossFloor() {
        return this.isLastFloor;
    }

    @Override
    public String toString() {
        return "FloorDetails [difficulty=" + this.difficulty + ", numberOfRooms=" + this.numberOfRooms
                + ", numberOfEnemies=" + this.numberOfEnemies + ", numberOfTreasure=" + this.numberOfTreasure
                + ", numberOfContraptions=" + this.numberOfContraptions + ", boosFloor=" + this.isLastFloor + "]";
    }

    @Override
    public int hashCode() {
        return this.difficulty.getLevelOfDifficulty() ^ this.numberOfContraptions ^ this.numberOfEnemies
                ^ this.numberOfTreasure ^ this.numberOfRooms ^ (this.isBossFloor() ? 1 : 0);
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj instanceof FloorDetailsImpl) {
            final FloorDetailsImpl other = (FloorDetailsImpl) obj;
            return this.difficulty == other.difficulty 
                    && this.numberOfContraptions == other.numberOfContraptions
                    && this.numberOfEnemies == other.numberOfEnemies 
                    && this.numberOfTreasure == other.numberOfTreasure
                    && this.numberOfRooms == other.numberOfRooms 
                    && this.isLastFloor == other.isLastFloor;
        }
        return false;
    }

}
