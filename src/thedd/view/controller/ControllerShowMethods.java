package thedd.view.controller;

import java.util.List;

import thedd.model.character.BasicCharacter;
import thedd.model.combat.action.result.ActionResult;

public interface ControllerShowMethods {
    
    public void showInventory();
    
    public void showActionSelector();
    
    void showTargets(List<BasicCharacter> characters);
    
    void hideTargets();
    
    void logAction(ActionResult result);
    
    void visualizeAction(ActionResult result);
    
}
