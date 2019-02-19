package combat.interfaces;

import java.util.List;

import combat.enums.ActionResultType;
import javafx.util.Pair;

public interface ActionResult {

    Action getAction();

    void addResult(ActionActor target, ActionResultType result);

    List<Pair<ActionActor, ActionResultType>> getResults();	

}
