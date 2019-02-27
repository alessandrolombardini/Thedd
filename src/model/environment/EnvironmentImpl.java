package model.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of {@link model.environment.Environment}.
 *
 */
public class EnvironmentImpl implements Environment {

    private final List<Floor> floors;
    private int actuaIndexFloor;

    /**
     * Environment constructor.
     * 
     * @param floor is the first floor of the environment
     * @throws NullPointerExeption if floor is null
     */
    public EnvironmentImpl(final Floor floor) {
        Objects.requireNonNull(floor);
        this.floors = new ArrayList<>();
        this.actuaIndexFloor = 0;
        this.floors.add(this.actuaIndexFloor, floor);
    }

    @Override
    public final void setNextFloor(final Floor floor) {
        Objects.requireNonNull(floor);
        this.actuaIndexFloor++;
        this.floors.add(actuaIndexFloor, floor);
    }

    @Override
    public final Floor getCurrentFloor() {
        return this.floors.get(this.actuaIndexFloor);
    }

    @Override
    public final int getCurrentFloorIndex() {
        return this.actuaIndexFloor;
    }

}
