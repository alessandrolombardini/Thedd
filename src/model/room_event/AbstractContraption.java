package model.room_event;

import java.util.Arrays;
import java.util.Objects;

import model.combat.implementations.AbstractActionActor;
import model.combat.interfaces.Action;

/**
 * 
 *
 */
public abstract class AbstractContraption extends AbstractActionActor implements Contraption {

    //private final Action action;
    private boolean completed;

    /**
     * 
     * @param name
     *          name of the contraption
     * @param action
     *          action performed by the contraption
     */
    public AbstractContraption(final String name, final Action action) {
        super(name);
        super.setAction(Objects.requireNonNull(action));
        this.getAction().ifPresent(a -> a.setSource(this));
        this.setAvailableActionsList(Arrays.asList(action));
        this.completed = false;
    }

    @Override
    public final RoomEventType getType() {
        return RoomEventType.CONTRAPTION;
    }
    
    @Override
    public boolean isCompleted() {
        return completed;
    }
    
    @Override
    public void complete() {
        if(!completed) {
            completed = !completed;
        }
    }

}
