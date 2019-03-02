package model.character;

import java.util.Optional;

/**
 * Implementation of a character factory.
 */
public final class CharacterFactoryImpl implements CharacterFactory {

    private static final CharacterFactory SINGLETON = new CharacterFactoryImpl();
    private static final int DEFAULT_MULTIPLIER = 1;
    private static final String DEFAULT_PC_NAME = "Player_Character";

    private boolean playerCharacterCreated = false;
    private boolean darkDestructorCreated = false;

    private CharacterFactoryImpl() {
    }

    /**
     * This method returns the univocal instance of the CharacterFactory.
     * 
     * @return a CharacterFactory.
     */
    public static CharacterFactory getFactory() {
        return SINGLETON;
    }

    @Override
    public Optional<BasicCharacter> createCharacter(final PlayerCharacter type, final Optional<String> name) {
        if (name.isPresent()) {
            if (!playerCharacterCreated) {
                playerCharacterCreated = true;
                return Optional.of(PlayerCharacter.createPlayerCharacter(name.get()));
            }
            return Optional.empty();
        }
        return Optional.of(PlayerCharacter.createPlayerCharacter(DEFAULT_PC_NAME));
    }

    @Override
    public BasicCharacter createCharacter(final HeadlessNPC type, final Optional<Integer> multiplier) {
        if (multiplier.isPresent()) {
            return HeadlessNPC.createHeadlessNPC(multiplier.get());
        }
        return HeadlessNPC.createHeadlessNPC(DEFAULT_MULTIPLIER);
    }

    @Override
    public BasicCharacter createCharacter(final GoblinNPC type, final Optional<Integer> multiplier) {
        if (multiplier.isPresent()) {
            return GoblinNPC.createGoblinNPC(multiplier.get());
        }
        return GoblinNPC.createGoblinNPC(DEFAULT_MULTIPLIER);
    }

    @Override
    public Optional<BasicCharacter> createCharacter(final DarkDestructorNPC type, final Optional<Integer> multiplier) {
        if (multiplier.isPresent()) {
            if (!darkDestructorCreated) {
                darkDestructorCreated = true;
                return Optional.of(DarkDestructorNPC.createDarkDestructorNPC(multiplier.get()));
            }
            return Optional.empty();
        }
        return Optional.of(DarkDestructorNPC.createDarkDestructorNPC(DEFAULT_MULTIPLIER));
    }

}
