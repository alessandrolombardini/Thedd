package model.item;

import model.combat.action.effect.ActionEffect;

/**
 * 
 *
 */
public interface StatisticBonusEffect extends ActionEffect {

    /**
     * Remove the bonus applied before.
     * @throws IllegalStateException if the effect is not applied before
     */
    void removeBonus();

}
