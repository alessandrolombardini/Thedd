package thedd.model.character.types;

import thedd.model.character.BasicCharacterImpl;

/**
 * Dark Destructor extension of {@link thedd.model.character.BasicCharacterImpl}.
 */
public class DarkDestructorNPC extends BasicCharacterImpl {

    /**
     * DarkDestructor's constructor.
     * 
     * @param name       name of this Boss.
     * @param multiplier rate multiplied at the basic statistics.
     */
    public DarkDestructorNPC(final String name, final double multiplier) {
        super(name, multiplier);
        // ret.addWeightedAction(new ActionImpl() , RandomActionPrority.DEFAULT);
    }
}
