package combat.implementations;

import combat.enums.TargetType;
import combat.interfaces.ActionActor;
import combat.interfaces.NPCAction;

public abstract class AbstractNPCAction extends AbstractAction implements NPCAction {

    private double weight;

    public AbstractNPCAction(final ActionActor source, final String name, final double weight, final TargetType targetType, final double baseHitChance) {
        super(source, name, baseHitChance, targetType);
        setPickWeight(weight);
        setTargetType(targetType);
    }

    @Override
    public void setPickWeight(final double weight) {
        this.weight = weight;
    }

    @Override
    public double getPickWeight() {
        return weight;
    }

}
