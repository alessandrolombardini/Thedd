package model.combat.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.util.Pair;
import model.combat.enums.ActionResultType;
import model.combat.interfaces.Action;
import model.combat.interfaces.ActionActor;
import model.combat.interfaces.ActionResult;

public class ActionResultImpl implements ActionResult {

    private final Action action;
    private final List<Pair<ActionActor, ActionResultType>> results;

    public ActionResultImpl(final Action action) {
        this.action = action;
        this.results = new ArrayList<Pair<ActionActor, ActionResultType>>();
    }

    @Override
    public Action getAction() {
        return action;
    }

    @Override
    public void addResult(final ActionActor target, final ActionResultType result) {
        results.add(new Pair<ActionActor, ActionResultType>(target, result));
    }

    @Override
    public List<Pair<ActionActor, ActionResultType>> getResults() {
        return Collections.unmodifiableList(results);
    }


}
