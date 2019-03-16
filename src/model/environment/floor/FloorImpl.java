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

    private static final int NONE_ROOMS = -1;

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
            throw new IllegalArgumentException();
        }
        this.factory = new RoomFactoryImpl(floorDetails, numberOfRooms, isLastFloor);
        this.rooms = new ArrayList<>();
        this.numberOfRooms = numberOfRooms;
        this.currentRoomIndex = NONE_ROOMS;
    }

    @Override
    public final boolean hasNextRoom() {
        return this.currentRoomIndex < (this.numberOfRooms - 1);
    }

    @Override
    public final boolean nextRoom() {
        if (!this.hasNextRoom()) {
            throw new IllegalStateException();
        } else if (!this.rooms.get(this.currentRoomIndex).checkToMoveOn()) {
            return false;
        }
        this.setNextRoom();
        return true;
    }

    @Override
    public final Room getCurrentRoom() {
        if (this.currentRoomIndex == NONE_ROOMS) {
            throw new IllegalStateException();
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

}
