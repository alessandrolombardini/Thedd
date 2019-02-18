package combat.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import combat.enums.ActionResultType;
import combat.interfaces.Action;
import combat.interfaces.ActionActor;
import combat.interfaces.ActionResult;
import javafx.util.Pair;

public class ActionResultImpl implements ActionResult {

	private final Action action;
	private final List<Pair<ActionActor, ActionResultType>> results;
	
	public ActionResultImpl(Action action) {
		this.action = action;
		this.results = new ArrayList<Pair<ActionActor, ActionResultType>>();
	}
	
	@Override
	public Action getAction() {
		return action;
	}

	@Override
	public void addResult(ActionActor target, ActionResultType result) {
		results.add(new Pair<ActionActor, ActionResultType>(target,result));
	}

	@Override
	public List<Pair<ActionActor, ActionResultType>> getResults() {
		return Collections.unmodifiableList(results);
	}


}
