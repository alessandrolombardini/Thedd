package combat.interfaces;

import java.util.List;

public interface AutomaticActionActor extends ActionActor {
 
    void setNextAction();

    void setNextTarget(List<ActionActor> availableTargets);

}
