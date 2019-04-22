package thedd.controller.information;

import java.util.List;

import thedd.model.character.BasicCharacter;
import thedd.model.combat.status.Status;

/**
 * This class represent an informations wrapper for a specified character's
 * statistics. The Statistics view-controller will ask from this class all the
 * required informations.
 */
public interface StatisticsInformation {

    /**
     * This method allows to update the current character from the one statistic's
     * information are taken.
     * 
     * @param character the character.
     */
    void setCharacter(BasicCharacter character);

    /**
     * This method returns a String representation of the type of the Character.
     * 
     * @return a {@link String}.
     */
    String getCharacterType();

    /**
     * This method returns actual selected charatcer's statuses.
     * 
     * @return a List<Status>
     */
    List<Status> getCharacterStatuses();

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
