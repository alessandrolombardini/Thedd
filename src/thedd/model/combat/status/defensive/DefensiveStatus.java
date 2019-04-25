package thedd.model.combat.status.defensive;

import java.util.Arrays;
import thedd.model.combat.modifier.HitChanceModifier;
import thedd.model.combat.modifier.ModifierActivation;
import thedd.model.combat.requirements.tags.TagRequirement;
import thedd.model.combat.requirements.tags.TagRequirementType;
import thedd.model.combat.status.StatusActivationFrequency;
import thedd.model.combat.status.StatusImpl;
import thedd.model.combat.tag.ActionTag;
import thedd.model.combat.tag.StatusTag;

/**
 * One time status which decreases the chances of offensive actions hitting the afflicted actor.
 */
public class DefensiveStatus extends StatusImpl {

    private static final double HITCHANCE_MODIFIER_VALUE = -0.1;
    private static final HitChanceModifier MODIFIER = new HitChanceModifier(HITCHANCE_MODIFIER_VALUE, false, ModifierActivation.ACTIVE_ON_DEFENCE);

    {
        MODIFIER.addRequirement(new TagRequirement<>(false, TagRequirementType.REQUIRED, Arrays.asList(ActionTag.OFFENSIVE)));
    }

    /**
     */
    public DefensiveStatus() {
        super("Defensive", new DefensiveStatusAction(MODIFIER), new DefensiveStatusActionDeact(MODIFIER), StatusActivationFrequency.ONE_TIME, 1, false);
        addTag(StatusTag.DEFENSIVE);
    }

}
