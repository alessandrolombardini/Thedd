package model.character;

import java.util.Optional;

/**
 * Interface of a character factory.
 */
public interface CharacterFactory {

    /**
     * This method return a new PlayerCharacter.
     * 
     * @param type the type of the character.
     * @param name the name of the player, the default is his type.
     * @return a new instance of PlayerCharacter
     */
    Optional<BasicCharacter> createCharacter(PlayerCharacter type, Optional<String> name);

    /**
     * This method returns a new Headless NPC.
     * 
     * @param type the type of the character.
     * @param multiplier it's the rate multiplied at the basic statistics.
     * @return a new instance of Headless.
     */
    BasicCharacter createCharacter(HeadlessNPC type, Optional<Integer> multiplier);

    /**
     * This method returns a new Goblin NPC.
     * 
     * @param type the type of the character.
     * @param multiplier it's the rate multiplied at the basic statistics.
     * @return a new instance of Goblin.
     */
    BasicCharacter createCharacter(GoblinNPC type, Optional<Integer> multiplier);

    /**
     * This method returns a new DarkDestructor NPC.
     * 
     * @param type the type of the character.
     * @param multiplier it's the rate multiplied at the basic statistics.
     * @return a new instance of DarkDestructor.
     */
    Optional<BasicCharacter> createCharacter(DarkDestructorNPC type, Optional<Integer> multiplier);

}
