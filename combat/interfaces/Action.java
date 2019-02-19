package combat.interfaces;

import java.util.List;
import combat.enums.TargetType;


public interface Action {

    //anche metodi per rimuovere gli effetti magari

    //public boolean checkRequirements()

    void setTargets(ActionActor target, List<ActionActor> targetedParty);

    void setSource(ActionActor source); //ActionActor può esserlo anche un interagibile

    ActionActor getSource();

    List<ActionEffect> getEffects();

    void addEffects(List<ActionEffect> effects);

    void addEffect(ActionEffect effect);

    void applyEffects(ActionActor target);

    String getName();

    String getLogMessage();  //Probabilmente dovrebbe essere l'effetto ad avere il log 

    List<ActionActor> getTargets();

    double getHitChanceModifier();

    TargetType getTargetType();

    void setTargetType(TargetType targetType);

}
