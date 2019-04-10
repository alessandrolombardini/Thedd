package thedd.model.combat.tag;

/**
 * A string used to define a specific quality of an entity.
 */
public interface Tag {

    /**
     * Gets the literal value of the Tag.
     * @return the value of the tag
     */
    String getLiteral();

    /**
     * Gets whether the tag should be displayed
     * to the player.<br>
     * Hidden tags may still be displayed, but non hidden
     * tags are always shown to the player.
     * @return true if the tag has to be shown to the player
     */
    boolean isHidden();

}
