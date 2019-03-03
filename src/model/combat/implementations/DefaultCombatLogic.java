package model.combat.implementations;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import model.combat.enums.ActionResultType;
import model.combat.enums.CombatStatus;
import model.combat.interfaces.Action;
import model.combat.interfaces.ActionActor;
import model.combat.interfaces.ActionResult;
import model.combat.interfaces.AutomaticActionActor;
import model.combat.interfaces.CombatInstance;
import model.combat.interfaces.CombatLogic;

public class DefaultCombatLogic implements CombatLogic {

    private CombatInstance combatInstance = new CombatInstanceImpl();
    private final List<ActionActor> actorsQueue = new LinkedList<>(); //Defined and handled here since the queue can change depending on the logic implementation
    private final Comparator<ActionActor> orderingStrategy = (a, b) -> b.getPriority() - a.getPriority();

    public DefaultCombatLogic() {
        this(Collections.<ActionActor>emptyList(), Collections.<ActionActor>emptyList());
    }

    public DefaultCombatLogic(final List<ActionActor> hostileNPCs) {
        this(hostileNPCs, Collections.<ActionActor>emptyList());
    }

    public DefaultCombatLogic(final List<ActionActor> hostileNPCs, final List<ActionActor> partyMembers) {
        combatInstance.addNPCsPartyMembers(hostileNPCs);
        combatInstance.addPlayerPartyMembers(partyMembers);
    }

    @Override
    public void addActorToQueue(final ActionActor actor) { 
        //Maybe checks if action is valid, but it's better to leave that directly to the Action (checkRequirements method) in order to ease the GUI's work
        if (!actor.getAction().equals(Optional.empty())) {
            actorsQueue.add(0, actor);
            if (combatInstance.getCombatStatus() != CombatStatus.ROUND_IN_PROGRESS) { //Se no vuol dire che siamo in caso counterattack o fuori combattimento
                actorsQueue.sort(orderingStrategy);
            } 
        }
    }

    public ActionResult executeNextAction() {
        combatInstance.setCombatStatus(CombatStatus.ROUND_IN_PROGRESS);
        final ActionActor source = actorsQueue.get(0);
        final Action sourceAction = source.getAction().get();
        final ActionResult result = new ActionResultImpl(sourceAction);
        final List<ActionActor> targets = sourceAction.getTargets(); 
        for (final ActionActor target : targets) {
            if (sourceAction.isTargetHit(target)) {

                //nel caso venga applicato un modificatore già presente da un'azione, resettare il modificatore, no stacking per ora
                //questo va nella logica degli effetti però

                //Parry attivabile anche se avversario colpisce? (Per ora no)
                //Se bersaglio in parry viene colpito, questo mantiene il parry? (Per ora sì)
                sourceAction.applyEffects(target);
                //CONTROLLA CHE IL BERSAGLIO SIA KO: NEL CASO, SE PRESENTE, RIMUOVILO DALLA QUEUE -> controllo effettuato in precedenza con canAct

                result.addResult(target, ActionResultType.HIT);

            } else if (/*source.action.isBlockable && */hasTargetParried(source, target)) { //in caso di parry si deve interrompere l'attacco? (al momento sì)
                actorsQueue.remove(target);
                combatInstance.setCombatStatus(CombatStatus.ROUND_PAUSED);
                if (combatInstance.getNPCsParty().contains(target)) { //Maybe check if target is Automatic AND his behavior is active
                    setNextAIMove((AutomaticActionActor) target);
                    combatInstance.setCombatStatus(CombatStatus.ROUND_IN_PROGRESS);
                }
                result.addResult(target, ActionResultType.PARRIED);

            } else {
                result.addResult(target, ActionResultType.MISSED);
            }
        }
        actorsQueue.remove(0);
        checkCombatStatus();
        return result;
    }

    private boolean hasTargetParried(final ActionActor source, final ActionActor target) {
        //INSTEAD OF INSTANCEOF, YOU CAN USE TAGS NOW!
        final Action targetAction = target.getAction().get();
        //se target è nella queue vuol dire che non ha ancora attivato il parry: ritorna false
        if (!actorsQueue.contains(target) && targetAction instanceof AbstractParryAction) {    //NOTA: alternativamente, posso aggiungere metodo getType() in Action, che restituisca che genere di Action è
             return targetAction.isTargetHit(source);
        } else {
            return false;
        }
    }

    private void checkCombatStatus() {

        if (actorsQueue.isEmpty() && combatInstance.getCombatStatus() == CombatStatus.ROUND_IN_PROGRESS) {
            combatInstance.setCombatStatus(CombatStatus.ROUND_ENDED);
        }

        /*if(combatInstance.getAllies().stream().allMatch(c -> c.getCharacter().getHealth == 0)) {
            combatInstance.setCombatStatus(CombatStatus.PLAYER_LOST);
            //reset characters turnOrder variable and isInCombat value
        }

        if(combatInstance.getHostiles().stream().allMatch(c -> c.getCharacter().getHealth == 0)) {
            combatInstance.setCombatStatus(CombatStatus.PLAYER_WON);
            //reset characters turnOrder variable and isInCombat value
        }*/

    }

    /*public void applyNextModifier() { //Da fare a fine turno, dopo aver eseguito tutte le azioni
     * //combatant.getModifiers, effect.updateEffect(combatant) -> 
     * effect.apply -> combatLogger.addStringLog (questo comporta anche lo scalare il suo turno di vita)
    }*/

    @Override
    public void resetCombat() {
        //resetto anche i modificatori oppure faccio che rimangono e riprendono al prossimo incontro?
        //Oppure espongo un metodo per applicarli manualmente (così qualcuno lo può richiamare quando pg si muove, usa qualcosa etc)
        //Oppure il controller ha un thread apposito che, ogni tot secondi, se il combattimento non è in corso, applica i buff/debuff
        combatInstance.resetInstance();
    }

    @Override
    public void startCombat() { 
        combatInstance.setCombatStatus(CombatStatus.STARTED); //forse da eliminare (o sostituire con round waiting)
        combatInstance.getAllParties().forEach(a -> a.setIsInCombat(true));
        prepareNextRound();
    }

    @Override
    public void prepareNextRound() {
        combatInstance.increaseRoundNumber();
        setNextAIMoves();
        final List<ActionActor> sortedActors = getOrderedActorsList();
        final int size = sortedActors.size();
        IntStream.range(0, size)
                .forEach(i -> sortedActors.get(i).setPlaceInRound(i + 1));
    }

    private void setNextAIMoves() {
        for (final ActionActor npc : combatInstance.getNPCsParty()) {
            if (npc instanceof AutomaticActionActor) {
                setNextAIMove((AutomaticActionActor) npc);
            } else {
                throw new IllegalStateException("Only AutomaticActionActors are allowed in the NPCs party");
            }
        }
    }

    private void setNextAIMove(final AutomaticActionActor actor) {
        //actor.selectNextAction(combatInstance) -> qua dentro l'attore richiama i metodi della sua strategia
        actor.setNextAction(combatInstance.getCopy());
        final List<ActionActor> availableTargets = actor.getAction().get().getValidTargets(combatInstance.getCopy());
        actor.setNextTarget(availableTargets);
        addActorToQueue(actor);
    }

    @Override
    public void setCombatInstance(final CombatInstance newInstance) {
        combatInstance = newInstance;
        //prepareNextRound();                      //FORSE UTILE SOLO IN CASO IN CUI IL ROUND SIA IN CORSO. OPPURE SE ROUND IN CORSO LANCIO ECCEZIONE
    }

    @Override
    public CombatStatus getCombatStatus() {
        return combatInstance.getCombatStatus();
    }

    @Override
    public boolean isRoundReady() {
        return combatInstance.getPlayerParty().size() + combatInstance.getNPCsParty().size() == actorsQueue.size()
                || combatInstance.getCombatStatus() == CombatStatus.ROUND_PAUSED; //Perchè in questa logica ogni attore ha un'azione da compiere
    }

    @Override
    public List<ActionActor> getOrderedActorsList() {
        final List<ActionActor> result = combatInstance.getAllParties();
        result.sort(orderingStrategy);
        return result;
    }

}
