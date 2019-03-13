package model.combat.implementations;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import model.combat.enums.ActionResultType;
import model.combat.enums.CombatStatus;
import model.combat.enums.ModifierActivation;
import model.combat.interfaces.Action;
import model.combat.interfaces.ActionActor;
import model.combat.interfaces.ActionResult;
import model.combat.interfaces.AutomaticActionActor;
import model.combat.interfaces.CombatInstance;
import model.combat.interfaces.CombatLogic;

/**
 *  Default implementation of the CombatLogic interface.
 *  It holds an instance of a {@link CombatInstance} and an ordered queue of {@link ActionActor}.<br>
 *  The queue is filled via addActorToQueue method and, when the executeNextAction method is called,
 *  the logic gets the first element of its queue, executes its action and updates the CombatStatus.
 *  <p>
 *  By default, actors are sorted comparing their priority, obtained via {@link ActionActor#getPriority()}
 */
public class DefaultCombatLogic implements CombatLogic {

    private CombatInstance combatInstance = new CombatInstanceImpl();
    private final List<ActionActor> actorsQueue = new LinkedList<>(); //Defined and handled here since the queue can change depending on the logic implementation
    private final Comparator<ActionActor> orderingStrategy = (a, b) -> b.getPriority() - a.getPriority();

    /**
     * Public constructor.
     */
    public DefaultCombatLogic() {
        this(Collections.<ActionActor>emptyList(), Collections.<ActionActor>emptyList());
    }

    /**
     * Public constructor.
     * @param hostileNPCs the List of Actors to placed in the party opposed to player's
     */
    public DefaultCombatLogic(final List<ActionActor> hostileNPCs) {
        this(hostileNPCs, Collections.<ActionActor>emptyList());
    }

    /**
     * Public constructor.
     * @param hostileNPCs the List of Actors to placed in the party opposed to player's
     * @param partyMembers the List of Actors to placed in the player's party
     */
    public DefaultCombatLogic(final List<ActionActor> hostileNPCs, final List<ActionActor> partyMembers) {
        combatInstance.addNPCsPartyMembers(hostileNPCs);
        combatInstance.addPlayerPartyMembers(partyMembers);
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
    public ActionResult executeNextAction() {
        combatInstance.setCombatStatus(CombatStatus.ROUND_IN_PROGRESS);
        final ActionActor source = actorsQueue.get(0);
        final Action sourceAction = source.getAction().get();
        final ActionResult result = new ActionResultImpl(sourceAction);
        final List<ActionActor> targets = sourceAction.getTargets(); 
        for (final ActionActor target : targets) {
            sourceAction.selectNextTarget();
            source.getActionModifiers().stream()
                                        .filter(m -> m.getModifierActivation() == ModifierActivation.ACTIVE_ON_ATTACK
                                                || m.getModifierActivation() == ModifierActivation.ALWAYS_ACTIVE)
                                        .filter(m -> m.accept(sourceAction))
                                        .forEach(m -> m.modify(sourceAction));
            if (sourceAction.isTargetHit()) {
                //nel caso venga applicato un modificatore già presente da un'azione, resettare il modificatore, no stacking per ora
                //questo va nella logica degli effetti però

                //Parry attivabile anche se avversario colpisce? (Per ora no)
                //Se bersaglio in parry viene colpito, questo mantiene il parry? (Per ora sì)
                sourceAction.applyEffects();
                //CONTROLLA CHE IL BERSAGLIO SIA KO: NEL CASO, SE PRESENTE, RIMUOVILO DALLA QUEUE -> controllo effettuato in precedenza con canAct

                result.addResult(target, ActionResultType.HIT);

            } else if (/*source.action.isBlockable && */hasTargetParried(source, target)) { //in caso di parry si deve interrompere l'attacco? (al momento sì)
                //execute parrying character action (maybe it can reflect the attackers' action etc...)
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
        final Action targetAction = target.getAction().get();
        //se target è nella queue vuol dire che non ha ancora attivato il parry: ritorna false
        if (!actorsQueue.contains(target) && targetAction.getTags().contains(new TagImpl("PARRY"))) {    //NOTA: alternativamente, posso aggiungere metodo getType() in Action, che restituisca che genere di Action è
            return targetAction.isTargetHit();
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

    /*public void applyStatuses() { //Da fare a fine turno, dopo aver eseguito tutte le azioni
     * //combatant.getModifiers, effect.updateEffect(combatant) -> 
     * effect.apply -> combatLogger.addStringLog (questo comporta anche lo scalare il suo turno di vita)
    }*/

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetCombat() {
        //resetto anche i modificatori oppure faccio che rimangono e riprendono al prossimo incontro?
        //Oppure espongo un metodo per applicarli manualmente (così qualcuno lo può richiamare quando pg si muove, usa qualcosa etc)
        //Oppure il controller ha un thread apposito che, ogni tot secondi, se il combattimento non è in corso, applica i buff/debuff
        combatInstance.resetInstance();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startCombat() { 
        combatInstance.setCombatStatus(CombatStatus.STARTED); //forse da eliminare (o sostituire con round waiting)
        combatInstance.getAllParties().forEach(a -> a.setIsInCombat(true));
        prepareNextRound();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void prepareNextRound() {
        combatInstance.increaseRoundNumber();
        setNextAIMoves();
        final List<ActionActor> sortedActors = getOrderedActorsList();
        final int size = sortedActors.size();
        IntStream.range(0, size)
                .forEach(i -> sortedActors.get(i).setPlaceInRound(i + 1));
        //foreach actor -> foreach status (which is an actor) -> addActortoqueue(status)
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

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCombatInstance(final CombatInstance newInstance) {
        combatInstance = newInstance;
        //prepareNextRound();                      //FORSE UTILE SOLO IN CASO IN CUI IL ROUND SIA IN CORSO. OPPURE SE ROUND IN CORSO LANCIO ECCEZIONE
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CombatStatus getCombatStatus() {
        return combatInstance.getCombatStatus();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRoundReady() {
        return combatInstance.getPlayerParty().size() + combatInstance.getNPCsParty().size() >= actorsQueue.size()
                || combatInstance.getCombatStatus() == CombatStatus.ROUND_PAUSED; //Perchè in questa logica ogni attore ha un'azione da compiere
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<ActionActor> getOrderedActorsList() {
        final List<ActionActor> result = combatInstance.getAllParties();
        result.sort(orderingStrategy);
        return result;
    }

}
