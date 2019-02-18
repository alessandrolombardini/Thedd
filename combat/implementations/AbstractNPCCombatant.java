package combat.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import combat.interfaces.Action;
import combat.interfaces.ActionActor;
import combat.interfaces.Combatant;
import combat.interfaces.NPCAction;
import combat.interfaces.NPCCombatant;

public abstract class AbstractNPCCombatant extends AbstractCombatant implements NPCCombatant {
	
	private List<NPCAction> actionList;
	
	public AbstractNPCCombatant(String name) {
		super(name);
	}
	
	@Override
	public void setAvailableActionsList(List<? extends Action> actions) {
		super.setAvailableActionsList(actions);
		actionList = getAvailableActionsList()
									.stream()
									.map(a -> (NPCAction)a)
									.collect(Collectors.toList());
		double totalActionsProbabilities = actionList.stream()
				.mapToDouble(a -> a.getPickChanceUpperBound())
				.sum();
		if(totalActionsProbabilities != 1.0) {
			throw new IllegalStateException("Actions pick chance's upper bounds do not amount to 1");
		}
	}
		
	@Override
	public void setNextAIAction() {
		Random dice = new Random();
		double diceRoll = dice.nextDouble();
		for(NPCAction action : actionList) {
			if(diceRoll > action.getPickChanceLowerBound() && diceRoll < action.getPickChanceUpperBound()) {
				setAction(action);
				setRandomTarget();
			};
		}
	}
	
	protected void setRandomTarget() {
		Random random = new Random();
		List<? extends Combatant> targetParty = new ArrayList<>();
		switch(getAction().get().getTargetType()) {
		case ALLY:
			targetParty = getCombatInstance().getNPCsParty();
			break;
		case EVERYONE:
			targetParty = getCombatInstance().getAllParties();
			break;
		case FOE:
			targetParty = getCombatInstance().getPlayerParty();
			break;
		case SELF:
			targetParty = getCombatInstance().getNPCsParty();
			break;
		default:
			throw new IllegalStateException("Action target type could not be found");
		}
		int targetIndex = random.nextInt(targetParty.size());
		setTargets(targetParty.get(targetIndex));
	}

}
