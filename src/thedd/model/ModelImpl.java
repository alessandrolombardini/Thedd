package thedd.model;

import java.util.Optional;

import thedd.model.world.environment.Session;

/**
 * Implementation of {@link thedd.model.Model}.
 */
public final class ModelImpl implements Model {

    private static final String ERROR_UNSETTEDSESSION = "Session is unsetted";

    private Optional<Session> session;

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
            throw new IllegalStateException(ERROR_UNSETTEDSESSION);
        }
        return this.session.get();
    }

}
