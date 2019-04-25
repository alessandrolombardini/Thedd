package thedd.model.combat.status.weakness;

import java.util.Arrays;

import thedd.model.character.statistics.Statistic;
import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageTypeImpl;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.action.effect.EquipmentStatisticChangerEffect;

/**
 * Action that triggers when {@link WeaknessStatus} is expired.<br>
 * Increases constitution, strength and agility.
 */
public class WeaknessStatusActionDeact extends ActionImpl {

    private static final String NAME = "Weakness";
    private static final double BASE_HITCHANCE = 1d;

    /**
     * Increases COS, STR and AGI of the source by one.
     */
    public WeaknessStatusActionDeact() {
        super(new ActionBuilder().setName(NAME)
                                 .setBaseHitChance(BASE_HITCHANCE)
                                 .setCategory(ActionCategory.STATUS)
                                 .setTargetType(TargetType.SELF)
                                 .setLogMessage(LogMessageTypeImpl.STATUS_EXPIRE)
                                 .build());
        final ActionEffect cosEffect = new EquipmentStatisticChangerEffect(Statistic.CONSTITUTION, 1);
        final ActionEffect strEffect = new EquipmentStatisticChangerEffect(Statistic.STRENGTH, 1);
        final ActionEffect agiEffect = new EquipmentStatisticChangerEffect(Statistic.AGILITY, 1);
        addEffects(Arrays.asList(cosEffect, strEffect, agiEffect));
    }

}
