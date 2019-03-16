package model.environment.floor;

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
    private int currentRoomIndex;

    /**
     * Floor constructor.
     * 
     * @param floorDetails contains the details of this floor
     * @param numberOfRooms define the number of rooms of the floor
     * @throws NullPointerExecption if roomFactory is null
     * @throws IllegalStateExeption if floor doesn't contains rooms
     */
    public FloorImpl(final FloorDetails floorDetails, final int numberOfRooms) {
        Objects.requireNonNull(floorDetails);
        this.factory = new RoomFactoryImpl(floorDetails, numberOfRooms);
        this.rooms = new ArrayList<>();
        this.numberOfRooms = numberOfRooms;
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

    private void setNextRoom() {
        this.currentRoomIndex++;
        this.rooms.add(this.currentRoomIndex, this.factory.createRoom());
    }

    @Override
    public boolean checkToChangeFloor() {
        return false;
    }

}
