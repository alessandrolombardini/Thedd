package model.item;

import model.combat.action.effect.ActionEffect;

/**
 * {@link model.combat.action.effect.ActionEffect} which change a {@link model.character.Statistic} 
 * of a {@link model.character.BasicCharacter}. It can also be removed and the bonus would no longer
 * be grated to the target after the removal.
 *
 */
public interface StatisticChangerEffect extends ActionEffect {

    /**
     * Remove the bonus applied before.
     * @throws IllegalStateException if the effect is not applied before
     */
    void removeBonus();

}
