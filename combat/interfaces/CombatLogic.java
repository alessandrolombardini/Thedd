package combat.interfaces;

import java.util.List;

import combat.enums.CombatStatus;

public interface CombatLogic {

    void setCombatInstance(CombatInstance newInstance);

    void setPlayerPendingAction(Action action);

    void addPlayerPendingActionTarget(ActionActor target);

    CombatStatus getCombatStatus();

    ActionResult executeNextAction();
    //NOTA: Probabilmente il Controller avrà bisogno di eseguire ogni azione manualmente, perchè deve leggere
    //il risultato dell'azione e, se opportuno, far partire le animazioni 

    void resetCombat();

    void startCombat();

    void prepareNextRound();

    void addActorToQueue(ActionActor action);

    boolean isRoundReady();

    List<ActionActor> getValidTargets(Action action);
}
