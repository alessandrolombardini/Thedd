package model.environment.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.environment.enums.Difficulty;
import model.environment.floor.Floor;
import model.environment.floor.FloorDetails;
import model.environment.floor.FloorDetailsFactory;
import model.environment.floor.FloorDetailsFactoryImpl;
import model.environment.floor.FloorImpl;

/**
 * Implementation of {@link model.environment.Environment}.
 *
 */
public class EnvironmentImpl implements Environment {

    /**
     * Minimun number of rooms.
     */
    public static final int MIN_NUMBER_OF_ROOMS = 1;
    /**
     * Minimum number of floors.
     */
    public static final int MIN_NUMBER_OF_FLOORS = 1;

    private static final int NONE_FLOOR = -1;

    private final List<Floor> floors;
    private final FloorDetailsFactory floorDeatailsFactory;
    private final int numberOfFloors;
    private final int numberOfRooms;
    private int actuaIndexFloor;

    /**
     * EnvironmentImpl constructor.
     * 
     * @param numberOfFloors is the number of floors of the map
     * @param numberOfRooms  is the number of rooms of each floor
     * @throws IllegalArgumentException if the number of floors or rooms is not valid
     */
    public EnvironmentImpl(final int numberOfFloors, final int numberOfRooms) {
        if (numberOfFloors < MIN_NUMBER_OF_FLOORS || numberOfRooms < MIN_NUMBER_OF_ROOMS) {
            throw new IllegalArgumentException();
        }
        this.floors = new ArrayList<>();
        this.floorDeatailsFactory = new FloorDetailsFactoryImpl();
        this.numberOfFloors = numberOfFloors;
        this.numberOfRooms = numberOfRooms;
        this.actuaIndexFloor = NONE_FLOOR;
    }

    @Override
    public final Floor getCurrentFloor() {
        if (this.getCurrentFloorIndex() == NONE_FLOOR) {
            throw new IllegalStateException();
        }
        return this.floors.get(this.actuaIndexFloor);
    }

    @Override
    public final int getCurrentFloorIndex() {
        return this.actuaIndexFloor;
    }

    @Override
    public final void setNextFloor(final FloorDetails floorDetails) {
        Objects.requireNonNull(floorDetails);
        if (!this.floors.isEmpty() && !this.floors.get(this.actuaIndexFloor).checkToChangeFloor()) {
            throw new IllegalStateException();
        }
        this.actuaIndexFloor++;
        this.floors.add(this.actuaIndexFloor, new FloorImpl(floorDetails, this.numberOfRooms, this.isCurrentLastFloor()));
    }

    @Override
    public final boolean isCurrentLastFloor() {
        return this.getCurrentFloorIndex() == (this.numberOfFloors - 1);
    }

    private boolean isNextLastFloor() {
        return (this.getCurrentFloorIndex() + 1) == (this.numberOfFloors - 1);
    }

    @Override
    public final List<FloorDetails> getFloorOptions() {
        if (this.isCurrentLastFloor()) {
            throw new IllegalStateException();
        }
        final List<FloorDetails> choices;
        choices = Stream.of(Difficulty.values())
                        .map(d -> this.floorDeatailsFactory.createFloorDetails(d, this.numberOfRooms, this.isNextLastFloor()))
                        .collect(Collectors.toList());
        return choices;
    }

    @Override
    public final String toString() {
        return "EnvironmentImpl [numberOfFloors=" + numberOfFloors + ", numberOfRooms=" + numberOfRooms
                + ", actuaIndexFloor=" + actuaIndexFloor + "]";
    }

}
