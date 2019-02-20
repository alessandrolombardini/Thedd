package combat.interfaces;

/**
 * An action that can be assigned and executed only by NPCs.
 */
public interface NPCAction extends Action {

    /**
     * Sets the weight used by the NPC to pick a random action.
     * <p>
     * @param weight a non negative number representing the weight of the action
     * @throws IllegalArgumentException if parameter is either lower than 0.0 or greater than 100.0
     */
    void setPickWeight(double weight);

    /**
     * Gets the weight of the action, to be used when randomly
     * selecting the next NPC action.
     * @return the weight of the action
     */
    double getPickWeight();

}
