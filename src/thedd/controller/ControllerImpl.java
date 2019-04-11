package thedd.controller;

import java.util.Objects;
import java.util.Optional;

import javafx.application.Platform;
import thedd.model.Model;
import thedd.model.ModelImpl;
import thedd.model.world.environment.EnvironmentImpl;
import thedd.model.world.environment.Session;
import thedd.model.world.environment.SessionImpl;
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
        if (this.checkNumber(numberOfRooms) && this.checkNumber(numberOfFloors)) {
            final int numOfRooms = Integer.parseInt(numberOfRooms);
            final int numOfFloors = Integer.parseInt(numberOfFloors);
            if (numOfFloors >= EnvironmentImpl.MIN_NUMBER_OF_FLOORS
                    || numOfRooms >= EnvironmentImpl.MIN_NUMBER_OF_ROOMS) {
                final Session session = new SessionImpl(Optional.ofNullable(playerName), numOfFloors, numOfRooms);
                this.model.setSession(session);
                return true;
            }
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void closeApplication() {
        Platform.exit();
    }

    private boolean checkNumber(final String number) {
        return !number.isEmpty() && number.chars().allMatch(Character::isDigit);
    }

}
