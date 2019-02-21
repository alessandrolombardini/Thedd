package combat.interfaces;

/**
 * TO BE IGNORED SINCE IT'S CURRENTLY NOT WELL DEFINED/HIGHLY SUSCEPTIBLE TO CHANGES.
 */
public interface Combatant extends ActionActor {

    void setCombatInstance(CombatInstance instance);

    void getCharacter(); //DA CAMBIARE

    //PER MARTINA: per il momento ignora la riga sottostante
    //lista di attributi (quantificati e semplici) che influenzeranno gli ActionEffects

    //PER MARTINA(2): nell'interfaccia combat logic mi sono appuntato un nuovo metodo (canChooseAction()):
    //                forse starebbe meglio nei personaggi? (comunque feature avanzata e legati agli 
    //                attributi/modificatori temporanei

    //bool isInCombat();
}
