package combat.interfaces;

public interface NPCAction extends Action {

	public void setPickChanceBounds(double lowerBound, double upperBound);
	
	public double getPickChanceLowerBound();
	
	public double getPickChanceUpperBound();
	
}
