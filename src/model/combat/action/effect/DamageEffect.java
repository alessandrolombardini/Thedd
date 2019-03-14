package model.combat.action.effect;


import java.util.stream.Collectors;

import model.combat.actor.ActionActor;


/**
 *  TO BE GENERALIZED AS AN INTERFACE
 *
 */
public class DamageEffect extends AbstractActionEffect {  //to be subdivided into physical damage/magical damage/fire/holy etc...

    private final double baseDamage;
    private double damage;

    public DamageEffect(final double baseAmount) {
        super();
        baseDamage = baseAmount;
    }

    @Override
    public void apply(final ActionActor target) {
        //if target is Character -> target.modifyHealth(amount)
        damage += baseDamage;
    }

    //GetDamagePreview ?
    //GetEffectPreview -> "Deals (average) #damage" / "Inflicts status.getName() status" / .. (one for each effect)

    @Override
    public void updateEffectByTarget(final ActionActor target) {
        super.updateEffectByTarget(target);
        // TODO Auto-generated method stub
        //foreach modifier attribute of the character, attr.modifyValue(damage)
    }

    @Override
    public void updateEffectBySource(final ActionActor source) {
        super.updateEffectBySource(source);
        // TODO Auto-generated method stub
        //Override default implementation (but use it!)
        //add character strenght etc.. to damage (if tag physical, else use faith/willpower)
    }

    @Override
    public String getLogMessage() {
        return "Dealt " + damage + " HP damage ";
    }

    public void setDamage(final double newDamage) {
        damage = newDamage;
    }

    public void addToDamage(final double damage) {
        this.damage += damage;
    }

    //Maybe turn DamageEffect into interface and implement it here
    public double getBaseDamage() {
        return baseDamage;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public String getDescription() {
        //This appends all the effect tags at the end of the description, maybe it would be better to
        //put the tags only on the action description.
        return "Deals " + damage + " HP damage \nTags:" + getTags().stream()
                                                            .map(t -> t.getLiteral()).collect(Collectors.joining(", "));
    }

    /*
    @Override
    public boolean equals(final Object other) {
        if (super.equals(other)) {
            final DamageEffect o = ((DamageEffect) other);
            return getBaseDamage() == o.getBaseDamage()
                    && getDamage() == o.getDamage();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(getBaseDamage(), getDamage());
    }*/
}
