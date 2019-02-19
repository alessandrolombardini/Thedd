package combat.interfaces;

public interface ModifierEffect extends ActionEffect {

    void updateDuration();

    boolean isExpired();

    String getLogMessage();

}
