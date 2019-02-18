package combat.implementations;

import java.util.List;

import combat.enums.TargetType;
import combat.interfaces.ActionActor;
import combat.interfaces.ActionEffect;

public abstract class AbstractParryAction extends AbstractAction {
	
	public AbstractParryAction(ActionActor source, String name, double baseHitChance) {
		super(source, name, baseHitChance, TargetType.SELF);
	}

	public AbstractParryAction(ActionActor source, String name, List<ActionEffect> effects, double baseHitChance) {
		super(source, name, effects, baseHitChance, TargetType.SELF);
	}

}
