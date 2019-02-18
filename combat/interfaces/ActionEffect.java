package combat.interfaces;

public interface ActionEffect {
	
	public void apply(ActionActor target);
	
	public void updateEffectByTarget(ActionActor target);
	
	public void updateEffectBySource(ActionActor source);
	
}
