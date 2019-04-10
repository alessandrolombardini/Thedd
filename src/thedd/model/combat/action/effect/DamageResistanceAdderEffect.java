package thedd.model.combat.action.effect;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

import thedd.model.combat.actor.ActionActor;
import thedd.model.combat.modifier.DamageModifier;
import thedd.model.combat.modifier.Modifier;
import thedd.model.combat.modifier.ModifierActivation;
import thedd.model.combat.requirements.tags.TagRequirement;
import thedd.model.combat.requirements.tags.TagRequirementType;
import thedd.model.combat.tag.EffectTag;

/**
 * {@link model.combat.action.effect.ActionEffect} which adds one {@link model.combat.modifier.DamageModifier}
 * to every {@link thedd.model.combat.action.effect.ActionEffect}s of every {@link thedd.model.combat.action.Action} 
 * of an {@link model.combat.actor.ActionActor} with the {@link model.combat.tag.Tag} specified in the modifier.
 * This represent a resistance to a type of damage. It can be a percentage or a flat value.
 *
 */
public final class DamageResistanceAdderEffect extends AbstractActionEffect implements RemovableEffect {

    private final Modifier<ActionEffect> modifier;
    private final boolean isPermanent;
    private final boolean isPercentage;
    private final double value;
    private final EffectTag resistanceTag;
    private Optional<ActionActor> target;

    /**
     * Create an effect which adds a resistance against a damage type.
     * @param value
     *          The value of the provided resistance
     * @param tagResisted
     *          The type of damage resisted
     * @param isPermanent
     *          Whether the resistance is permanent or not
     * @param isPercentage
     *          Whether the value is a percentage
     */
    public DamageResistanceAdderEffect(final double value, final EffectTag tagResisted, final boolean isPermanent, final boolean isPercentage) {
        super();
        this.value = value;
        this.isPercentage = isPercentage;
        this.isPermanent = isPermanent;
        this.resistanceTag = tagResisted;
        this.modifier = new DamageModifier(this.value, this.isPercentage, ModifierActivation.ACTIVE_ON_DEFENCE);
        this.modifier.addRequirement(new TagRequirement<>(false, TagRequirementType.REQUIRED, Arrays.asList(tagResisted)));
    }

    @Override
    public void remove() {
        if (!target.isPresent()) {
            throw new IllegalStateException("This effect is not applied to any target;");
        } else {
            if (!isPermanent) {
                this.target.get().removeEffectModifier(modifier);
            }
        }
    }

    @Override
    public void apply(final ActionActor target) {
        this.target = Optional.of(Objects.requireNonNull(target));
        this.target.get().addEffectModifier(modifier, isPermanent);
    }

    @Override
    public String getLogMessage() {
        return "Added additional resistance up to " + value + (isPercentage ? "% to " : " to ") + resistanceTag.getLiteral();
    }

    @Override
    public String getDescription() {
        return "Adds additional resistance up to " + value + (isPercentage ? "% to " : " to ") + resistanceTag.getLiteral();
    }

    @Override
    public String getPreviewMessage() {
        return getDescription();
    }

    @Override
    public ActionEffect getCopy() {
        return new DamageResistanceAdderEffect(value, resistanceTag, isPermanent, isPercentage);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        final int ternaryTruePrime = 1231;
        final int ternaryFalsePrime = 1237;
        int result = super.hashCode();
        result = prime * result + (isPercentage ? ternaryTruePrime : ternaryFalsePrime);
        result = prime * result + (isPermanent ? ternaryTruePrime : ternaryFalsePrime);
        result = prime * result + ((resistanceTag == null) ? 0 : resistanceTag.hashCode());
        long temp;
        temp = Double.doubleToLongBits(value);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DamageResistanceAdderEffect other = (DamageResistanceAdderEffect) obj;
        if (isPercentage != other.isPercentage) {
            return false;
        }
        if (isPermanent != other.isPermanent) {
            return false;
        }
        if (resistanceTag != other.resistanceTag) {
            return false;
        }
        return Double.doubleToLongBits(value) == Double.doubleToLongBits(other.value);
    }


}
