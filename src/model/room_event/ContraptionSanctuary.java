package model.room_event;

import java.util.ArrayList;

import model.combat.enums.TargetType;
import model.combat.implementations.AbstractAction;
import model.combat.interfaces.Action;

public class ContraptionSanctuary extends ContraptionImpl {

    private static final String NAME = "Sanctuary";
    private static final Action ACTION = new AbstractAction(null, "Sanctuary", new ArrayList<>(), 1.0, TargetType.EVERYONE) { };
    
    public ContraptionSanctuary() {
        super(NAME, ACTION);
        //TODO add healing effect 
    }
    
    public static Contraption newInstance() {
        return new ContraptionSanctuary();
    }
    
    
}
