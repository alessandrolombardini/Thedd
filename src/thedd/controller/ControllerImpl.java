package thedd.controller;

import java.util.Objects;

import thedd.model.Model;
import thedd.model.ModelImpl;
import thedd.view.View;

/**
 * Implementation of the {@link Controller}.
 */
public class ControllerImpl implements Controller {

    private final View view;
    private final Model model;

    /**
     * Create a new Controller instance.
     * 
     * @param view
     *          view reference
     */
    public ControllerImpl(final View view) {
        Objects.requireNonNull(view);
        this.view = view;
        this.model = new ModelImpl();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final boolean newGame(final String playerName, final String numberOfRooms, final String numberOfFloors) {
        final SessionBuilder sessionBuilder = model.getSessionBuilder();
        sessionBuilder.playerCharacterName(playerName);
        if (this.checkNumber(numberOfRooms) && this.checkNumber(numberOfFloors)) {
            final int numOfRooms = Integer.parseInt(numberOfRooms);
            final int numOfFloors = Integer.parseInt(numberOfFloors);
            if (numOfFloors >= EnvironmentImpl.MIN_NUMBER_OF_FLOORS
                    || numOfRooms >= EnvironmentImpl.MIN_NUMBER_OF_ROOMS) {
                sessionBuilder.numberOfFloors(numOfFloors);
                sessionBuilder.numberOfRooms(numOfRooms);
                this.model.setSession(sessionBuilder.build());
                return true;
            }
        }
        return false;
    }

    private boolean checkNumber(final String number) {
        return !number.isEmpty() && number.chars().allMatch(Character::isDigit);
    }
}
