package model.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Implementation of {@link model.environment.Floor}.
 *
 */
public class FloorImpl implements Floor {

    private final RoomFactory factory;
    private final List<Room> rooms;
    private final int numberOfRooms;
    private final boolean isBossFloor;
    private int currentRoomIndex;

    /**
     * Floor constructor.
     * 
     * @param roomFactory is the room factory of the floor
     * @param isBossFloor define if the floor is the last
     * @param numberOfRooms define the number of rooms of the floor
     * @throws NullPointerExecption if roomFactory is null
     * @throws IllegalStateExeption if floor doesn't contains rooms
     */
    public FloorImpl(final RoomFactory roomFactory, final boolean isBossFloor, final int numberOfRooms) {
        Objects.requireNonNull(roomFactory);
        this.factory = roomFactory;
        this.rooms = new ArrayList<>();
        this.numberOfRooms = numberOfRooms;
        this.isBossFloor = isBossFloor;
        this.currentRoomIndex = -1;
        this.nextRoom();
    }

    @Override
    public final boolean hasNextRoom() {
        return this.currentRoomIndex < this.numberOfRooms;
    }

    @Override
    public final boolean nextRoom() {
        if (!this.hasNextRoom()) {
            throw new IllegalStateException();
        } else if (!this.rooms.isEmpty() && !this.rooms.get(this.currentRoomIndex).checkToMoveOn()) {
            return false;
        }
        this.setNextRoom();
        return true;
    }

    @Override
    public final Room getCurrentRoom() {
        return this.rooms.get(this.currentRoomIndex);
    }

    @Override
    public final int getCurrentRoomIndex() {
        return this.currentRoomIndex;
    }

    @Override
    public final boolean isLastFloor() {
        return this.isBossFloor;
    }

    private void setNextRoom() {
        this.currentRoomIndex++;
        this.rooms.add(this.currentRoomIndex, this.factory.createRoom());
    }

}
