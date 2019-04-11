package thedd.model.combat.action.effect;

/**
 * {@link model.combat.action.effect.ActionEffect} which change a {@link model.character.Statistic} 
 * of a {@link model.character.BasicCharacter}. It can also be removed and the bonus would no longer
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
