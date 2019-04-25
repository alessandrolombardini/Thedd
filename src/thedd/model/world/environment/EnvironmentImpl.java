package thedd.model.world.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import thedd.model.world.Difficulty;
import thedd.model.world.floor.Floor;
import thedd.model.world.floor.FloorDetailsFactory;
import thedd.model.world.floor.FloorDetailsFactoryImpl;
import thedd.model.world.floor.FloorImpl;
import thedd.model.world.floor.details.FloorDetails;

/**
 * Implementation of {@link thedd.model.world.environment.Environment}.
 *
 */
public class EnvironmentImpl implements Environment {

    /**
     * Minimum number of rooms in game.
     */
    public static final int MIN_NUMBER_OF_ROOMS = 1;

    /**
     * Minimum number of floors in game.
     */
    public static final int MIN_NUMBER_OF_FLOORS = 1;

    /**
     * Index of none floors.
     */
    public static final int NONE_FLOORS = -1;

    private static final String ERROR_OUTOFRANGE = "Number of floors or rooms is out of range";
    private static final String ERROR_LASTFLOOR = "No more floors available";
    private static final String ERROR_UNSETTEDFLOOR = "No floors setted";
    private static final Difficulty INIT_DIFFICULTY_LEVEL = Difficulty.NORMAL;

    private final List<Floor> floors;
    private final FloorDetailsFactory floorDeatailsFactory;
    private final int numberOfFloors;
    private final int numberOfRooms;
    private Optional<List<FloorDetails>> floorOptions;
    private int actuaIndexFloor;

    /**
     * EnvironmentImpl constructor.
     * 
     * @param numberOfFloors is the number of floors of the map
     * @param numberOfRooms  is the number of rooms of each floor
     * @throws IllegalArgumentException if the number of floors or rooms is not
     *                                  valid
     */
    public EnvironmentImpl(final int numberOfFloors, final int numberOfRooms) {
        if (numberOfFloors < MIN_NUMBER_OF_FLOORS || numberOfRooms < MIN_NUMBER_OF_ROOMS) {
            throw new IllegalArgumentException(ERROR_OUTOFRANGE);
        }
        this.floorDeatailsFactory = new FloorDetailsFactoryImpl();
        this.floors = new ArrayList<>();
        this.numberOfFloors = numberOfFloors;
        this.numberOfRooms = numberOfRooms;
        this.floorOptions = Optional.empty();
        this.actuaIndexFloor = NONE_FLOORS;
        this.initEnvironment();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final Floor getCurrentFloor() {
        if (this.getCurrentFloorIndex() == NONE_FLOORS) {
            throw new IllegalStateException(ERROR_UNSETTEDFLOOR);
        }
        return this.floors.get(this.getCurrentFloorIndex());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getCurrentFloorIndex() {
        return this.actuaIndexFloor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean setNextFloor(final FloorDetails floorDetails) {
        Objects.requireNonNull(floorDetails);
        if (!this.floors.isEmpty() && !this.floors.get(this.getCurrentFloorIndex()).checkToChangeFloor()) {
            return false;
        } else if (!this.floorOptions.isPresent() || !this.floorOptions.get().contains(floorDetails)) {
            return false;
        }
        this.actuaIndexFloor++;
        this.floorOptions = Optional.empty();
        this.floors.add(this.actuaIndexFloor, new FloorImpl(floorDetails));
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean isCurrentLastFloor() {
        return this.getCurrentFloorIndex() == (this.numberOfFloors - 1);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final List<FloorDetails> getFloorOptions() {
        if (this.isCurrentLastFloor()) {
            throw new IllegalStateException(ERROR_LASTFLOOR);
        } else if (this.floorOptions.isPresent()) {
            return this.floorOptions.get();
        }
        final List<FloorDetails> choices;
        final boolean isNextBossFloor = this.getCurrentFloorIndex() == this.numberOfFloors - 2;
        choices = Stream.of(Difficulty.values())
                        .map(d -> this.floorDeatailsFactory.createFloorDetails(d, this.numberOfRooms, isNextBossFloor))
                        .collect(Collectors.toList());
        this.floorOptions = Optional.of(choices);
        return choices;
    }

    private void initEnvironment() {
        final List<FloorDetails> floorOptions = this.getFloorOptions();
        final int easyIndex = floorOptions.indexOf(floorOptions.stream()
                                                               .filter(f -> f.getDifficult() == INIT_DIFFICULTY_LEVEL)
                                                               .findAny()
                                                               .get());
        this.setNextFloor(floorOptions.get(easyIndex));
    }

    @Override
    public final String toString() {
        return "EnvironmentImpl [numberOfFloors=" + this.numberOfFloors + ", numberOfRooms=" + this.numberOfRooms
                + ", actuaIndexFloor=" + this.actuaIndexFloor + "]";
    }
}
