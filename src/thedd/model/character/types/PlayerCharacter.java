package thedd.model.character.types;

import java.util.EnumMap;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import thedd.model.character.BasicCharacter;
import thedd.model.character.BasicCharacterImpl;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.StatValuesImpl;
import thedd.model.character.statistics.Statistic;
import thedd.model.combat.action.TargetType;
import thedd.model.combat.action.implementations.ActiveDefence;
import thedd.model.combat.action.implementations.DivineIntervention;
import thedd.model.combat.action.implementations.HeavyAttack;
import thedd.model.combat.action.implementations.LightAttack;
import thedd.model.item.Item;
import thedd.model.item.ItemRarityImpl;
import thedd.model.item.equipableitem.implementations.EquipableItemChest;
import thedd.model.item.equipableitem.implementations.EquipableItemHelmet;
import thedd.model.item.equipableitem.implementations.EquipableItemShield;
import thedd.model.item.equipableitem.implementations.EquipableItemSword;
import thedd.model.item.usableitem.UsableItemPotion;

/**
 * Player Character extension of
 * {@link thedd.model.character.BasicCharacterImpl}.
 */
public class PlayerCharacter extends BasicCharacterImpl {

    private final Random seed;
    private final EnumMap<Statistic, StatValues> stat;
    private static final String DEFAULT_NAME = "Player";
    private static final int BASE_AGILITY = 5;
    private static final int VARIATION_AGILITY = 2;
    private static final int BASE_HEALTH = 120;
    private static final int VARIATION_HEALTH = 30;
    private static final int BASE_CONSTITUTION = 6;
    private static final int VARIATION_CONSTITUTION = 2;
    private static final int BASE_STRENGTH = 4;
    private static final int VARIATION_STRENGTH = 3;

    /**
     * PlayerCharacter's constructor.
     * 
     * @param name the string name of the player character.
     */
    public PlayerCharacter(final String name) {
        super(name, true);
        seed = new Random();
        stat = new EnumMap<>(Statistic.class);
        initStatValues();
        this.setStatistics(stat);
        initInventory();
        addActionToAvailable(new LightAttack(TargetType.EVERYONE));
        addActionToAvailable(new HeavyAttack(TargetType.EVERYONE));
        addActionToAvailable(new ActiveDefence());
        addActionToAvailable(new DivineIntervention(TargetType.EVERYONE));
    }

    private void initStatValues() {
        final int value = seed.nextInt(VARIATION_HEALTH + 1) + BASE_HEALTH;
        this.stat.put(Statistic.HEALTH_POINT, new StatValuesImpl(value, value));
        this.stat.put(Statistic.AGILITY,
                new StatValuesImpl(seed.nextInt(VARIATION_AGILITY + 1) + BASE_AGILITY, StatValuesImpl.NO_MAX));
        this.stat.put(Statistic.CONSTITUTION, new StatValuesImpl(
                seed.nextInt(VARIATION_CONSTITUTION + 1) + BASE_CONSTITUTION, StatValuesImpl.NO_MAX));
        this.stat.put(Statistic.STRENGTH,
                new StatValuesImpl((seed.nextInt(VARIATION_STRENGTH + 1) + BASE_STRENGTH), StatValuesImpl.NO_MAX));
    }

    private void initInventory() {
        this.getInventory().addItem(UsableItemPotion.getNewInstance(ItemRarityImpl.COMMON));
        this.getInventory().addItem(EquipableItemSword.getNewInstance(ItemRarityImpl.COMMON));
        this.getInventory().addItem(EquipableItemShield.getNewInstance(ItemRarityImpl.COMMON));
        this.getInventory().addItem(EquipableItemChest.getNewInstance(ItemRarityImpl.COMMON));
        this.getInventory().addItem(EquipableItemHelmet.getNewInstance(ItemRarityImpl.COMMON));
        this.getInventory().getAll().stream().filter(Item::isEquipable).forEach(item -> this.equipItem(item));
    }

    /**
     * Static Factory Method that create a new Player Character.
     * 
     * @param name string representation choice of player's name.
     * @return a Player Character.
     * @throws NullPointerException if parameter is null.
     */
    public static BasicCharacter getNewInstance(final Optional<String> name) {
        Objects.requireNonNull(name);
        return new PlayerCharacter((name.isPresent() && !name.get().equals("")) ? name.get() : DEFAULT_NAME);
    }
}
