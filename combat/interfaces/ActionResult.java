package combat.interfaces;

import java.util.List;

import combat.enums.ActionResultType;
import javafx.util.Pair;

public interface ActionResult {
	
	public Action getAction();
	
	public void addResult(ActionActor target, ActionResultType result);
	
	public List<Pair<ActionActor, ActionResultType>> getResults();	
	
}
