package combat.implementations;

import combat.interfaces.ActionActor;
import combat.interfaces.ActionEffect;

public class DamageEffect implements ActionEffect {  //physical damage/magical damage/fire/holy etc...
	
	private double damage;
	
	public DamageEffect(double baseAmount) {
		this.damage = baseAmount;
	}
	
	@Override
	public void apply(ActionActor target) {
		//if target is Character -> target.modifyHealth(amount)
		System.out.println("Target hit: dealt " + damage + " damage");
	}

	@Override
	public void updateEffectByTarget(ActionActor target) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateEffectBySource(ActionActor source) {
		// TODO Auto-generated method stub
		
	}

}
