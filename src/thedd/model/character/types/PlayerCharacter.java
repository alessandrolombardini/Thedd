package thedd.model.character.types;

import thedd.model.character.BasicCharacterImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.implementations.ActiveDefence;
import thedd.model.combat.action.implementations.DivineIntervention;
import thedd.model.combat.action.implementations.HeavyAttack;
import thedd.model.combat.action.implementations.LightAttack;

/**
 * Player Character extension of {@link thedd.model.character.BasicCharacterImpl}.
 */
public class PlayerCharacter extends BasicCharacterImpl {

    /**
     * PlayerCharacter's constructor.
     * 
     * @param name 
     *          the string name of the player character.
     * @param multiplier 
     *          rate multiplied at the basic statistics.
     */
    public PlayerCharacter(final String name, final double multiplier) {
        super(name, multiplier, true);
        addActionToAvailable(new LightAttack(TargetType.EVERYONE));
        addActionToAvailable(new HeavyAttack(TargetType.EVERYONE));
        addActionToAvailable(new ActiveDefence());
        addActionToAvailable(new DivineIntervention(TargetType.EVERYONE));
    }
}
