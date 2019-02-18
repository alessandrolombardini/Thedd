package combat.implementations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import combat.enums.TargetType;
import combat.interfaces.ActionActor;
import combat.interfaces.Combatant;
import combat.interfaces.NPCAction;
import combat.interfaces.NPCCombatant;

public abstract class AbstractNPCCombatant extends AbstractCombatant implements NPCCombatant {

	private List<NPCAction> actionList = new ArrayList<>();
	private Random dice = new Random();
	
	public AbstractNPCCombatant(String name) {
		super(name);
	}
	
	@Override
	public void setAIActions(List<NPCAction> actions) {
		actionList = Collections.unmodifiableList(actions);
		double totalActionsProbabilities = actionList.stream()
				.mapToDouble(a -> a.getPickChanceUpperBound())
				.sum();
		if(totalActionsProbabilities != 1.0) {
			throw new IllegalStateException("Actions pick chance's upper bounds do not amount to 1");
		}
	}
		
	@Override
	public void setNextAIAction(ActionActor target) {
		double diceRoll = dice.nextDouble();
		for(NPCAction action : actionList) {
			if(diceRoll > action.getPickChanceLowerBound() && diceRoll < action.getPickChanceUpperBound()) {
				setAction(action);
				if(target != null) {
					setTarget(target);
				} else {
					setRandomTarget();
				}
			};
		}
	}
	
	@Override
	public void setNextAIAction() {
		setNextAIAction(null);
	}
	
	private void setRandomTarget() {
		Random random = new Random();
		if(getCurrentAction().get().getTargetType() == TargetType.FOE) {
			List<Combatant> playerParty = getInstance().getPlayerParty();
			int targetIndex = random.nextInt(playerParty.size());
			setTarget(playerParty.get(targetIndex));
		}
	}

}
