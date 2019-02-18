package combat.interfaces;

public interface ModifierEffect extends ActionEffect {
	
	public void updateDuration();
	
	public boolean isExpired();
	
	public String getLogMessage();

}
