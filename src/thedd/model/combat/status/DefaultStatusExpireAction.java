package thedd.model.combat.status;

import thedd.model.combat.action.ActionBuilder;
import thedd.model.combat.action.ActionCategory;
import thedd.model.combat.action.ActionImpl;
import thedd.model.combat.action.LogMessageType;
import thedd.model.combat.action.TargetType;

/** 
 * Default action that signals when a status is expired.
 */
public class DefaultStatusExpireAction extends ActionImpl {

    private static final double BASE_HITCHANCE = 1d;

    /**
     * @param statusName, the name of the expired status
     */
    public DefaultStatusExpireAction(final String statusName) {
        super(new ActionBuilder().setName(statusName)
                                 .setBaseHitChance(BASE_HITCHANCE)
                                 .setCategory(ActionCategory.STATUS)
                                 .setTargetType(TargetType.SELF)
                                 .setLogMessage(LogMessageType.STATUS_EXPIRE)
                                 .build());
    }

}
