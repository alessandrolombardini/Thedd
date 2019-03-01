package model.room_event;

import java.util.ArrayList;

import model.combat.enums.TargetType;
import model.combat.implementations.AbstractAction;
import model.combat.interfaces.Action;
import model.item.HealingEffect;

public class ContraptionSanctuary extends ContraptionImpl {

    private static final String NAME = "Sanctuary";
    private static final Action ACTION = new AbstractAction(null, "Sanctuary", new ArrayList<>(), 1.0, TargetType.EVERYONE) { };
    
    public ContraptionSanctuary() {
        super(NAME, ACTION);
        ACTION.addEffect(new HealingEffect(Double.POSITIVE_INFINITY));
    }
    
    public static Contraption newInstance() {
        return new ContraptionSanctuary();
    }
    
}
