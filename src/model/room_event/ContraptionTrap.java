package model.room_event;

import java.util.ArrayList;

import model.combat.enums.TargetType;
import model.combat.implementations.AbstractAction;
import model.combat.implementations.DamageEffect;
import model.combat.interfaces.Action;

public class ContraptionTrap extends AbstractContraption {

    private static final String NAME = "Trap";
    private static final Action ACTION = new AbstractAction(null, NAME, new ArrayList<>(), 1.0, TargetType.EVERYONE) { }; 
    private static final double BASE_DAMAGE = 20.0;
            
    public ContraptionTrap() {
        super(NAME, ACTION);
        this.getAction().get().addEffect(new DamageEffect(BASE_DAMAGE));    
    }
    
    public static Contraption createNew() {
        return new ContraptionTrap();
    }
    
    public static Contraption newInstance() {
        return new ContraptionTrap();
    }
    
}
