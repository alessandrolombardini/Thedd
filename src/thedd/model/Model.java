package thedd.model;


import thedd.model.world.environment.SessionImpl.SessionBuilder;

/**
 * This class describe the model of the pattern MVC.
 *
 */
public interface Model {

    /**
     * This method allows to get a session builder.
     * 
     * @return SessionBuilder
     */
    SessionBuilder getSessionBuilder();

    /**
     * This method allows to set a new game session.
     * 
     * @param session represent the new game session
     * @return true if param session is valid and there isn't session alredy setted
     */
    boolean setSession(Session session);

    /**
     * This method allows to get the current session.
     * 
     * @return the current Session
     */
    Session getSession();

}
