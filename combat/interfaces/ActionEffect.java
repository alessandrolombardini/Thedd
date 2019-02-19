package combat.interfaces;

public interface ActionEffect {

    void apply(ActionActor target);

    void updateEffectByTarget(ActionActor target);

    void updateEffectBySource(ActionActor source);
    
    String getLogMessage();

}
