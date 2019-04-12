package thedd.controller;

import java.util.List;
import thedd.model.item.Item;

/**
 * This class represent an informations wrapper for characters. This class
 * contains informations about the specified character from the constructor and
 * view will ask from this class all the required informations.
 */
public interface CharacterInformations {

    /**
     * Returns the quantity of the specified item on the character's inventory.
     * 
     * @param item the specified item
     * @return the quantity
     */
    String getInventoryItemQuantity(Item item);

    /**
     * Returns a list of all the Items in character's inventory.
     * 
     * @return a list of Item
     */
    List<Item> getAllItemsList();

    /**
     * This method returns true if the item is equipped, otherwise false.
     * 
     * @param item the item searched into equipped list.
     * @return a boolean value.
     */
    boolean isEquipped(Item item);

    /**
     * Return the actual value of character's health points.
     * 
     * @return a String
     */
    String getHealthPointValue();

    /**
     * Return the actual value of character's constitution status.
     * 
     * @return a String
     */
    String getConstitutionValue();

    /**
     * Return the actual value of character's strength status.
     * 
     * @return a String
     */
    String getStrengthValue();

    /**
     * Return the actual value of character's agility status.
     * 
     * @return a String
     */
    String getAgilityValue();

    /**
     * Return the max value of character's health points.
     * 
     * @return a String
     */
    String getHealthPointMaxValue();

    /**
     * Return the max value of character's constitution status.
     * 
     * @return a String
     */
    String getConstitutionMaxValue();

    /**
     * Return the max value of character's strength status.
     * 
     * @return a String
     */
    String getStrengthMaxValue();

    /**
     * Return the max value of character's agility status.
     * 
     * @return a String
     */
    String getAgilityMaxValue();
}
