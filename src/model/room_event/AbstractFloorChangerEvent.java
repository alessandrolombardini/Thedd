package model.room_event;

import java.util.function.BooleanSupplier;

/**
 * Abstract implementation of {@link model.room_event.FloorChanger}. 
 * Every specialization must specify whether it is mandatory or not.
 *
 */
public abstract class AbstractFloorChangerEvent extends AbstractRoomEvent implements FloorChangerEvent {

    private final BooleanSupplier condition;

    /**
     * 
     * @param name
     *  the name of the {@link model.room_event.FloorChanger}.
     * @param condition
     *  the condition that has to be met to be able to change floor.
     */
    public AbstractFloorChangerEvent(final String name, final BooleanSupplier condition) {
        super(name);
        this.condition = condition; 
    }

    @Override
    public final RoomEventType getType() {
        return RoomEventType.FLOOR_CHANGER_EVENT;
    }

    @Override
    public final boolean isCompleted() {
        return false;
    }

    @Override
    public abstract boolean isSkippable();

    @Override
    public final boolean isConditionMet() {
        return condition.getAsBoolean();
    }

}
