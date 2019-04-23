package thedd.model.combat.action.effect;

/**
 * ActionEffect which can be removed and the effect will no longer
 * be grated to the target after the removal.
 *
 */
public interface RemovableEffect extends ActionEffect {

    /**
     * Remove the effect applied before.
     * @throws IllegalStateException if the effect is not applied before
     */
    void remove();

}
