package combat.implementations;

import combat.interfaces.ActionActor;
import combat.interfaces.ActionEffect;

public class DamageEffect implements ActionEffect {  //physical damage/magical damage/fire/holy etc...

    private double damage;

    public DamageEffect(final double baseAmount) {
        this.damage = baseAmount;
    }

    @Override
    public void apply(final ActionActor target) {
        //if target is Character -> target.modifyHealth(amount)
    }

    @Override
    public void updateEffectByTarget(final ActionActor target) {
        // TODO Auto-generated method stub
    }

    @Override
    public void updateEffectBySource(final ActionActor source) {
        // TODO Auto-generated method stub
    }

}
