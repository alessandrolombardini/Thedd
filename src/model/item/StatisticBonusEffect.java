package model.item;

import model.combat.interfaces.ActionEffect;

interface StatisticBonusEffect extends ActionEffect {

    /**
     * Remove the bonus applied before.
     * @throws IllegalStateException if the effect is not applied before
     */
    void removeBonus();

}
