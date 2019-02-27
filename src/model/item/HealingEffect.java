package model.item;

import model.combat.interfaces.ActionActor;
import model.combat.interfaces.ActionEffect;

public class HealingEffect implements ActionEffect {

    private final double baseHealing;

    public HealingEffect(final double healingValue) {
        baseHealing = healingValue;
    }

    @Override
    public void apply(final ActionActor target) {
        if (target instanceof model.character.Character) {

        }

    }

    @Override
    public void updateEffectByTarget(final ActionActor target) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateEffectBySource(final ActionActor source) {
        // TODO Auto-generated method stub

    }

    @Override
    public final String getLogMessage() {
        return " is healed for " + baseHealing + " PV.";
    }

}
