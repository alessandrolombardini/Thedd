package model.combat.action.result;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.combat.action.Action;
import model.combat.actor.ActionActor;

/**
 * Basic implementation of the ActionResult interface.
 */
public class ActionResultImpl implements ActionResult {

    private final Action action;
    //TODO: change immutableEntry to Apache Pair
    private final List<AbstractMap.SimpleImmutableEntry<ActionActor, ActionResultType>> results;

    /**
     * Public constructor.
     * @param action the action associated with the result
     */
    public ActionResultImpl(final Action action) {
        this.action = action;
        this.results = new ArrayList<AbstractMap.SimpleImmutableEntry<ActionActor, ActionResultType>>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getAction() {
        return action;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResult(final ActionActor target, final ActionResultType result) {
        results.add(new AbstractMap.SimpleImmutableEntry<ActionActor, ActionResultType>(target, result));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AbstractMap.SimpleImmutableEntry<ActionActor, ActionResultType>> getResults() {
        return Collections.unmodifiableList(results);
    }


}
