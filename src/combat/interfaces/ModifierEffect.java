package combat.interfaces;

/**
 * TO BE IGNORED SINCE IT'S CURRENTLY NOT WELL DEFINED/HIGHLY SUSCEPTIBLE TO CHANGES.
 */
public interface ModifierEffect extends ActionEffect {

    void updateDuration();

    boolean isExpired();

    String getLogMessage();

}
