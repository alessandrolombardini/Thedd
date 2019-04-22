package thedd.model.character.types;

import java.util.ArrayList;
import java.util.List;

import thedd.model.character.BasicCharacterImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.action.implementations.ActiveDefence;
import thedd.model.combat.action.implementations.FieryTouch;
import thedd.model.combat.action.implementations.HeavyAttack;
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
 * Dark Destructor extension of {@link thedd.model.character.BasicCharacterImpl}.
 */
public class DarkDestructor extends BasicCharacterImpl {

    /**
     * DarkDestructor's constructor.
     * 
     * @param name       name of this Boss.
     * @param multiplier rate multiplied at the basic statistics.
     */
    public DarkDestructor(final String name, final double multiplier) {
        super(name, multiplier, false);
        // ret.addWeightedAction(new ActionImpl() , RandomActionPrority.DEFAULT);
        setPermanentModifiers();
    }

    private void setPermanentModifiers() {
        final ModifierActivation defensive = ModifierActivation.ACTIVE_ON_DEFENCE;
        final List<Tag> requiredTags = new ArrayList<Tag>();
        final List<Tag> allowedTags = new ArrayList<Tag>();

        //Resistance to physical damage
        final Modifier<ActionEffect> damageResistance = new DamageModifier(-0.3, false, true, defensive);
        allowedTags.add(EffectTag.NORMAL_DAMAGE);
        allowedTags.add(EffectTag.AP_DAMAGE);
        damageResistance.addRequirement(new TagRequirement<>(false, TagRequirementType.ALLOWED, allowedTags));
        addEffectModifier(damageResistance, true);

        //Weakness to holy damage
        final Modifier<ActionEffect> holyDmgWeakness = new DamageModifier(0.2, false, true, defensive);
        requiredTags.add(EffectTag.HOLY_DAMAGE);
        holyDmgWeakness.addRequirement(new TagRequirement<>(false, TagRequirementType.REQUIRED, requiredTags));
        addEffectModifier(holyDmgWeakness, true);

        addWeightedAction(new LightAttack(TargetType.FOE), RandomPrority.HIGH);
        addWeightedAction(new FieryTouch(TargetType.FOE), RandomPrority.LOW);
        addWeightedAction(new ActiveDefence(), RandomPrority.LOW);
        addWeightedAction(new HeavyAttack(TargetType.FOE), RandomPrority.DEFAULT);
    }
}
