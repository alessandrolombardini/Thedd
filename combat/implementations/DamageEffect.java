package combat.implementations;

import combat.interfaces.ActionActor;
import combat.interfaces.ActionEffect;

public class DamageEffect implements ActionEffect {  //to be subdivided into physical damage/magical damage/fire/holy etc...

    private double damage; 
    private String targetName;
    private String sourceName;

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
        targetName = target.getName();
    }

    @Override
    public void updateEffectBySource(final ActionActor source) {
        // TODO Auto-generated method stub
        sourceName = source.getName();
    }

    @Override
    public String getLogMessage() {
        return sourceName + " succesfully hits " + targetName + " dealing " + damage + " HP damage ";
    }

}
