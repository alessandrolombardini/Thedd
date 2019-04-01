package thedd.model;

import java.util.Optional;

import thedd.model.world.environment.Session;
import thedd.model.world.environment.SessionImpl;
import thedd.model.world.environment.SessionImpl.SessionBuilder;

/**
 * Implementation of {@link thedd.model.Model}.
 */
public final class ModelImpl implements Model {

    private Optional<Session> session;

    /**
     * Model' constructor.
     */
    public ModelImpl() { }

    /**
     * {@inheritDoc}
     */
    @Override
    public SessionBuilder getSessionBuilder() {
        return new SessionImpl.SessionBuilder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean setSession(final Session session) {
        if (this.session.isPresent()) {
            return false;
        }
        this.session = Optional.of(session);
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Session getSession() {
        if (!this.session.isPresent()) {
            throw new IllegalStateException();
        }
        return this.session.get();
    }

}
