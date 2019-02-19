package combat.interfaces;

public interface Combatant extends ActionActor {

    void setCombatInstance(CombatInstance instance); //Probabilmente serve solo agli NPC

    void getCharacter(); //DA CAMBIARE

    //lista di attributi (quantificati e semplici) che influenzeranno gli ActionEffects

}
