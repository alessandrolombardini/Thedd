package combat.interfaces;

import combat.enums.TargetType;

public interface NPCAction extends Action {
	
	public void setTargetType(TargetType targetType);
	
	public TargetType getTargetType();

	public void setPickChanceBounds(double lowerBound, double upperBound);
	
	public double getPickChanceLowerBound();
	
	public double getPickChanceUpperBound();
	
}
