package combat.implementations;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import combat.enums.ActionResultType;
import combat.enums.CombatStatus;
import combat.enums.TargetType;
import combat.interfaces.Action;
import combat.interfaces.ActionActor;
import combat.interfaces.ActionResult;
import combat.interfaces.AutomaticActionActor;
import combat.interfaces.CombatInstance;
import combat.interfaces.CombatLogic;
import combat.interfaces.Combatant;
import combat.interfaces.NPCCombatant;

public class DefaultCombatLogic implements CombatLogic {

    private CombatInstance combatInstance = new CombatInstanceImpl();
    private final List<ActionActor> actorsQueue = new LinkedList<>(); //Defined and handled here since the queue can change depending on the logic implementation

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
        actorsQueue.add(0, actor);
        if (combatInstance.getCombatStatus() != CombatStatus.ROUND_IN_PROGRESS) { //Se no vuol dire che siamo in caso counterattack
            actorsQueue.sort((a, b) -> a.getPriority() - b.getPriority());
        }
    }

    public ActionResult executeNextAction() {
        combatInstance.setCombatStatus(CombatStatus.ROUND_IN_PROGRESS);
        final ActionActor source = actorsQueue.get(0);
        final Action sourceAction = source.getAction().get();
        final ActionResult result = new ActionResultImpl(sourceAction);
        final List<ActionActor> targets = sourceAction.getTargets(); 
        for (final ActionActor target : targets) {
            if (isTargetHit(sourceAction, target)) {

                //nel caso venga applicato un modificatore già presente da un'azione, resettare il modificatore, no stacking per ora
                //questo va nella logica degli effetti però

                //Parry attivabile anche se avversario colpisce? (Per ora no)
                //Se bersaglio in parry viene colpito, questo mantiene il parry? (Per ora sì)

                sourceAction.applyEffects(target);
                //CONTROLLA CHE IL BERSAGLIO SIA KO: NEL CASO, SE PRESENTE, RIMUOVILO DALLA QUEUE

                result.addResult(target, ActionResultType.HIT);

            } else if (hasTargetParried(source, target)) { //in caso di parry si deve interrompere l'attacco? (al momento sì)
                actorsQueue.remove(target);
                if (target instanceof NPCCombatant) { //Controlla se confronto fra class funziona comunque: sarebbe meglio
                    ((NPCCombatant) target).setNextAIAction();
                    actorsQueue.add(target);
                } else {
                    combatInstance.setCombatStatus(CombatStatus.ROUND_PAUSED);
                    break; //Brutto?
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

    private void checkCombatStatus() {

        if (actorsQueue.isEmpty() && combatInstance.getCombatStatus() == CombatStatus.ROUND_IN_PROGRESS) {
            combatInstance.setCombatStatus(CombatStatus.ROUND_ENDED);
        }

        /*if(combatInstance.getAllies().stream().allMatch(c -> c.getCharacter().getHealth == 0)) {
            combatInstance.setCombatStatus(CombatStatus.PLAYER_LOST);
        }

        if(combatInstance.getHostiles().stream().allMatch(c -> c.getCharacter().getHealth == 0)) {
            combatInstance.setCombatStatus(CombatStatus.PLAYER_WON);
        }*/

    }

    private boolean hasTargetParried(final ActionActor source, final ActionActor target) {
        final Action targetAction = target.getAction().get();
        //se target è nella queue vuol dire che non ha ancora attivato il parry: ritorna false
        if (!actorsQueue.contains(target) && targetAction instanceof AbstractParryAction) {    //NOTA: alternativamente, posso aggiungere metodo getType() in Action, che restituisca che genere di Action è
             return isTargetHit(targetAction, source);  //Nota: se parry bersaglia se stesso, questa funzione resutuirà sempre true: parry automatico oppure librera utils diceRoller perchè mi sono rotto le balls di aggiungere random a caso
        } else {
            return false;
        }
    }

    /*public void applyNextModifier() { //Da fare a fine turno, dopo aver eseguito tutte le azioni
     * //combatant.getModifiers, effect.updateEffect(combatant) -> 
     * effect.apply -> combatLogger.addStringLog (questo comporta anche lo scalare il suo turno di vita)
    }*/

    private boolean isTargetHit(final Action action, final ActionActor target) { 
        final Random dice = new Random(); //classe utils.DiceRoller potrebbe essere comoda?
        double sourceModifier, actionModifier, targetModifier, sourceEffectsModifiers, targetEffectsModifier;

        actionModifier = action.getHitChanceModifier();

        if (action.getTargetType() != TargetType.ALLY && action.getTargetType() != TargetType.SELF &&
                getValidTargets(action).contains(target)) { 
            /*
             * sourceChance = currentActor.getPriority() (e getPriority richiamerà getCurrentReflexes a sto punto)
             * modificare poi baseChance in modo da decidersi
             */
            //if target.getClass() == ActionActor.class -> targetModifier = target.getReflexes etc...
            //if (target.getAction() instanceof AbstractDefensiveAction) {
                    //defenceModifier = target.getAction().getModifier()
            //}
            //successThreshold = tutti i modificatori sopra combinati in qualche modo (addizione probs)
            final double successThreshold = actionModifier;
            return dice.nextDouble() < successThreshold;
        } else {
            return true;
        }
    }

    @Override
    public void resetCombat() {
        //resetto anche i modificatori oppure faccio che rimangono e riprendono al prossimo incontro?
        //Oppure espongo un metodo per applicarli manualmente (così qualcuno lo può richiamare quando pg si muove, usa qualcosa etc)
        //Oppure il controller ha un thread apposito che, ogni tot secondi, se il combattimento non è in corso, applica i buff/debuff
        combatInstance.resetInstance();
    }

    @Override
    public void startCombat() { 
        combatInstance.setCombatStatus(CombatStatus.ROUND_ENDED);//forse da eliminare (o sostituire con round waiting)
        prepareNextRound();
    }

    @Override
    public void prepareNextRound() {
        combatInstance.increaseRoundNumber();
        setNextAIMoves();
    }

    private void setNextAIMoves() {
        for (final ActionActor npc : combatInstance.getNPCsParty()) {
            if (npc instanceof AutomaticActionActor) {
                ((AutomaticActionActor) npc).setNextAction();
                addActorToQueue(npc);
            } else {
                throw new IllegalStateException("Only AutomaticActionActors are allowed in the NPCs party");
            }
        }
    }

    @Override
    public void setCombatInstance(final CombatInstance newInstance) {
        combatInstance = newInstance;
        //prepareNextRound();                      //FORSE UTILE SOLO IN CASO IN CUI IL ROUND SIA IN CORSO. OPPURE SE ROUND IN CORSO LANCIO ECCEZIONE
    }

    @Override
    public void setPlayerPendingAction(final Action action) {
        //session.getCurrentCharacter.setAction? Altrimenti gli passo pure il personaggio
    }

    @Override
    public void addPlayerPendingActionTarget(final ActionActor target) {
        //guarda sopra, dopodichè inserisci nella coda
    }

    @Override
    public CombatStatus getCombatStatus() {  ///GET ROUND RESULT FORSE VA MEGLIO
        return combatInstance.getCombatStatus();
    }

    @Override
    public boolean isRoundReady() {
        return combatInstance.getPlayerParty().size() + combatInstance.getNPCsParty().size() == actorsQueue.size()
                || combatInstance.getCombatStatus() == CombatStatus.ROUND_PAUSED; //Perchè in questa logica ogni attore ha un'azione da compiere
    }

    @Override
    public List<ActionActor> getValidTargets(final Action action) {
        List<ActionActor> targets = new LinkedList<>();
        switch (action.getTargetType()) {
        case ALLY:
            break;
        case EVERYONE:
            targets = combatInstance.getAllParties();
            break;
        case FOE:
            targets = NPCCombatant.class.isInstance(action.getSource()) ? combatInstance.getPlayerParty() :
                    combatInstance.getNPCsParty();
            break;
        case SELF:
            targets = Collections.singletonList((Combatant) action.getSource());
            break;
        default:
            throw new IllegalStateException("Target type of the action was not found");
        }
        return targets;
    }

}
