package thedd.model.combat.tag;

import java.util.Set;

/**
 * An entity that can hold a collection of {@link Tag}.
 */
public interface Taggable {

    /**
     * Gets the Set of {@link Tag}.
     * @return a Set of Tags 
     */
    Set<Tag> getTags();

    /**
     * Adds one or more {@link Tag} to the Set.
     * @param tags the Set of tags to be added
     * @param arePermanent true if the tags are going to be not removable, false otherwise
     */
    void addTags(Set<Tag> tags, boolean arePermanent);

    /**
     * Adds a {@link Tag} to the Set.
     * @param isPermanent true if the tag is going to be not removable, false otherwise
     * @param tag the tag to be added
     */
    void addTag(Tag tag, boolean isPermanent);

    /**
     * Removes a {@link Tag} to the Set, if the the provided tag is not permanent.
     * @param tag the tag to be removed
     * @return true if the tag was removed, false otherwise
     */
    boolean removeTag(Tag tag);

}
