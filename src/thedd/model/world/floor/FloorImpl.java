package thedd.model.world.floor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import thedd.model.world.floor.FloorDetails.FloorDetails;
import thedd.model.world.room.Room;
import thedd.model.world.room.RoomFactory;
import thedd.model.world.room.RoomFactoryImpl;

/**
 * Implementation of {@link thedd.model.world.floor.Floor}.
 */
public class FloorImpl implements Floor {

    private static final String ERROR_UNSETTEDROOM = "No rooms are setted";
    private static final String ERROR_UNVAILABLEROOM = "No room available";
    private static final int NONE_ROOMS = -1;

    private final RoomFactory factory;
    private final List<Room> rooms;
    private final int numberOfRooms;
    private int currentRoomIndex;

    /**
     * Floor constructor.
     * 
     * @param floorDetails contains the details of this floor
     * @throws NullPointerExecption if roomFactory is null
     */
    public FloorImpl(final FloorDetails floorDetails) {
        Objects.requireNonNull(floorDetails);
        this.factory = new RoomFactoryImpl(floorDetails);
        this.numberOfRooms = floorDetails.getNumberOfRooms();
        this.rooms = new ArrayList<>();
        this.currentRoomIndex = NONE_ROOMS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean hasNextRoom() {
        return this.getCurrentRoomIndex() < (this.numberOfRooms - 1);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    @Override
    public final Room getCurrentRoom() {
        if (this.currentRoomIndex == NONE_ROOMS) {
            throw new IllegalStateException(ERROR_UNSETTEDROOM);
        }
        return this.rooms.get(this.getCurrentRoomIndex());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final int getCurrentRoomIndex() {
        return this.currentRoomIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean checkToChangeFloor() {
        return !this.hasNextRoom() && this.rooms.get(this.getCurrentRoomIndex()).checkToMoveOn();
    }

    private void setNextRoom() {
        this.currentRoomIndex++;
        this.rooms.add(this.getCurrentRoomIndex(), this.factory.createRoom());
    }

    @Override
    public final String toString() {
        return "FloorImpl [rooms=" + this.rooms + ", numberOfRooms=" + this.numberOfRooms + ", currentRoomIndex="
                + this.currentRoomIndex + "]";
    }

    @Override
    public final int hashCode() {
        return currentRoomIndex ^ numberOfRooms ^ rooms.hashCode();
    }

    @Override
    public final boolean equals(final Object obj) {
        if (obj instanceof FloorImpl) {
            final FloorImpl other = (FloorImpl) obj;
            return this.currentRoomIndex == other.currentRoomIndex 
                    && this.numberOfRooms == other.numberOfRooms
                    && this.rooms.equals(other.rooms);
        }
        return false;
    }

}
