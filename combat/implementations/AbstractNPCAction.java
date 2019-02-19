package combat.implementations;

import combat.enums.TargetType;
import combat.interfaces.ActionActor;
import combat.interfaces.NPCAction;

public abstract class AbstractNPCAction extends AbstractAction implements NPCAction {

    private double lowerBound;
    private double upperBound = 100;

    public AbstractNPCAction(final ActionActor source, final String name, final double lowerBound, final double upperBound, final TargetType targetType, final double baseHitChance) {
        super(source, name, baseHitChance, targetType);
        setPickChanceBounds(lowerBound, upperBound);
        setTargetType(targetType);
    }

    @Override
    public void setPickChanceBounds(final double lowerBound, final double upperBound) {
        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("Inserted lower bound cannot be higher than upper bound");
        }
        this.lowerBound = lowerBound < 0 ? 0 : lowerBound > 1 ? 1 : lowerBound;
        this.upperBound = upperBound < 0 ? 0 : upperBound > 1 ? 1 : upperBound;
    }

    @Override
    public double getPickChanceLowerBound() {
        return lowerBound;
    }

    @Override
    public double getPickChanceUpperBound() {
        return upperBound;
    }

}
