package model.combat.implementations;

import java.util.List;

import model.combat.enums.TargetType;
import model.combat.interfaces.ActionActor;
import model.combat.interfaces.ActionEffect;

public abstract class AbstractParryAction extends ActionImpl {

    public AbstractParryAction(final ActionActor source, final String name, final double baseHitChance) {
        super(source, name, baseHitChance, TargetType.SELF);
    }

    public AbstractParryAction(final ActionActor source, final String name, final List<ActionEffect> effects, final double baseHitChance) {
        super(source, name, effects, baseHitChance, TargetType.SELF);
    }

}
