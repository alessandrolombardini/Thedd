package thedd.model.character.types;

import java.util.ArrayList;
import java.util.List;

import thedd.model.character.BasicCharacterImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.action.implementations.LightAttack;
import thedd.model.combat.modifier.DamageModifier;
import thedd.model.combat.modifier.Modifier;
import thedd.model.combat.modifier.ModifierActivation;
import thedd.model.combat.requirements.tags.TagRequirement;
import thedd.model.combat.requirements.tags.TagRequirementType;
import thedd.model.combat.tag.EffectTag;
import thedd.model.combat.tag.Tag;
import thedd.utils.randomcollections.RandomPrority;

/**
 * Goblin extension of {@link thedd.model.character.BasicCharacterImpl}.
 */
public class Goblin extends BasicCharacterImpl {

    /**
     * Goblin's constructor.
     * 
     * @param name       name of this NPC.
     * @param multiplier rate multiplied at the basic statistics.
     */
    public Goblin(final String name, final double multiplier) {
        super(name, multiplier);
        setPermanentModifiers();
        this.addWeightedAction(new LightAttack(TargetType.FOE), RandomPrority.DEFAULT);
    }

    private void setPermanentModifiers() {
        final ModifierActivation defensive = ModifierActivation.ACTIVE_ON_DEFENCE;
        final List<Tag> requiredTags = new ArrayList<Tag>();

        final Modifier<ActionEffect> poisonResistance = new DamageModifier(-0.8, true, defensive);
        requiredTags.add(EffectTag.POISON_DAMAGE);
        poisonResistance.addRequirement(new TagRequirement<>(false, TagRequirementType.REQUIRED, requiredTags));
        addEffectModifier(poisonResistance, true);
    }
}
