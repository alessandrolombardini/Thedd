package combat.implementations;

import java.util.List;

import combat.enums.TargetType;
import combat.interfaces.ActionActor;
import combat.interfaces.ActionEffect;

public abstract class AbstractParryAction extends AbstractAction {

    public AbstractParryAction(final ActionActor source, final String name, final double baseHitChance) {
        super(source, name, baseHitChance, TargetType.SELF);
    }

    public AbstractParryAction(final ActionActor source, final String name, final List<ActionEffect> effects, final double baseHitChance) {
        super(source, name, effects, baseHitChance, TargetType.SELF);
    }

}
