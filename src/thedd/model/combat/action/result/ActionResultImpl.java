package thedd.model.combat.action.result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.tuple.ImmutablePair;
import thedd.model.combat.action.Action;
import thedd.model.combat.actor.ActionActor;

/**
 * Basic implementation of the ActionResult interface.
 */
public class ActionResultImpl implements ActionResult {

    private final Action action;
    private final List<ImmutablePair<ActionActor, ActionResultType>> results;

    /**
     * Public constructor.
     * @param action the action associated with the result
     */
    public ActionResultImpl(final Action action) {
        this.action = action;
        this.results = new ArrayList<ImmutablePair<ActionActor, ActionResultType>>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Action getAction() {
        return action.getCopy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResult(final ActionActor target, final ActionResultType result) {
        results.add(new ImmutablePair<ActionActor, ActionResultType>(target, result));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ImmutablePair<ActionActor, ActionResultType>> getResults() {
        return Collections.unmodifiableList(results);
    }


}
