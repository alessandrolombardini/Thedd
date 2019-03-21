
package model.environment.floor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.environment.environment.EnvironmentImpl;
import model.environment.room.Room;
import model.environment.room.RoomFactory;
import model.environment.room.RoomFactoryImpl;

/**
 * Implementation of {@link model.environment.Floor}.
 *
 */
public class FloorImpl implements Floor {

    /**
     * Index of none rooms.
     */
    public static final int NONE_ROOM = -1;

    private static final String ERROR_OUTOFRANGE = "The number of rooms is out of range";
    private static final String ERROR_UNSETTEDROOM = "No roooms are setted";
    private static final String ERROR_UNVAILABLEROOM = "No room available";

    private final RoomFactory factory;
    private final List<Room> rooms;
    private final int numberOfRooms;
    private int currentRoomIndex;

    /**
     * Floor constructor.
     * 
     * @param floorDetails  contains the details of this floor
     * @param numberOfRooms define the number of rooms of the floor
     * @param isLastFloor   is true if this is the last floor
     * @throws NullPointerExecption    if roomFactory is null
     * @throws IllegalArgumentExeption if floor doesn't contains enought rooms
     */
    public FloorImpl(final FloorDetails floorDetails, final int numberOfRooms, final boolean isLastFloor) {
        Objects.requireNonNull(floorDetails);
        if (numberOfRooms < EnvironmentImpl.MIN_NUMBER_OF_ROOMS) {
            throw new IllegalArgumentException(ERROR_OUTOFRANGE);
        }
        this.factory = new RoomFactoryImpl(floorDetails, numberOfRooms, isLastFloor);
        this.rooms = new ArrayList<>();
        this.numberOfRooms = numberOfRooms;
        this.currentRoomIndex = NONE_ROOM;
    }

    @Override
    public final boolean hasNextRoom() {
        return this.currentRoomIndex < (this.numberOfRooms - 1);
    }

    @Override
    public final boolean nextRoom() {
        if (!this.hasNextRoom()) {
            throw new IllegalStateException(ERROR_UNVAILABLEROOM);
        } else if (!this.rooms.isEmpty() && !this.rooms.get(this.currentRoomIndex).checkToMoveOn()) {
            return false;
        }
        this.setNextRoom();
        return true;
    }

    @Override
    public final Room getCurrentRoom() {
        if (this.currentRoomIndex == NONE_ROOM) {
            throw new IllegalStateException(ERROR_UNSETTEDROOM);
        }
        return this.rooms.get(this.currentRoomIndex);
    }

    @Override
    public final int getCurrentRoomIndex() {
        return this.currentRoomIndex;
    }

    @Override
    public final boolean checkToChangeFloor() {
        return !this.hasNextRoom() && this.rooms.get(this.currentRoomIndex).checkToMoveOn();
    }

    private void setNextRoom() {
        this.currentRoomIndex++;
        this.rooms.add(this.currentRoomIndex, this.factory.createRoom());
    }

    @Override
    public final String toString() {
        return "FloorImpl [rooms=" + rooms + ", numberOfRooms=" + numberOfRooms + ", currentRoomIndex="
                + currentRoomIndex + "]";
    }

    @Override
    public final int hashCode() {
        return currentRoomIndex + numberOfRooms + rooms.hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (!Objects.nonNull(obj) ||  !(obj instanceof FloorImpl)) {
            return false;
        } 
        final FloorImpl other = (FloorImpl) obj;
        if (this.currentRoomIndex != other.currentRoomIndex || this.numberOfRooms != other.numberOfRooms
                || !this.rooms.equals(other.rooms)) {
            return false;
        }
        return true;
    }

}
