package combat.interfaces;

public interface NPCAction extends Action {

    void setPickChanceBounds(double lowerBound, double upperBound);

    double getPickChanceLowerBound();

    double getPickChanceUpperBound();

}
