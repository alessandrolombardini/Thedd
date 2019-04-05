package thedd.model.combat.modifier;

import java.util.List;

import thedd.model.combat.common.Modifiable;
import thedd.model.combat.requirements.Requirement;

public class StatBasedModifier implements Modifier<Modifiable> {

    private final Modifier<Modifiable> modifier;

    public StatBasedModifier(/*Statisic/Character+targetStat, multiplier*/final Modifier<Modifiable> modifier) {
        this.modifier = modifier;
    }

    @Override
    public void setValue(double value) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setIsPercentage(boolean isPercentage) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public double getValue() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isPercentage() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public ModifierActivation getModifierActivation() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setModifierActivation(ModifierActivation type) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean accept(Modifiable modifiable) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void modify(Modifiable modifiable) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addRequirement(Requirement<Modifiable> requirement) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Requirement<Modifiable>> getRequirements() {
        // TODO Auto-generated method stub
        return null;
    }

}
