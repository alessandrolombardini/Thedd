package combat.interfaces;

import java.util.List;
import java.util.Optional;

public interface ActionActor {

    String getName();

    Optional<Action> getAction();

    void setAction(Action action);

    void setTargets(ActionActor target);

    int compareTo(ActionActor other);

    int getPriority();

    void setAvailableActionsList(List<? extends Action> actions);

    List<? extends Action> getAvailableActionsList();

    //compareTo, se la source non è un interactable, compara l'agilità dei due personaggi. Se source è un interactable, ha default priorità minore
    //alternativa: ActionActor implementa metodo per ritornare una priorità (interactable ritornerà -1, personaggi la loro statistica, effetti duraturi una loro iniziativa (oppure -1 e gli interactable -2))

}
