package thedd.model.character;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

import thedd.model.character.inventory.Inventory;
import thedd.model.character.inventory.InventoryImpl;
import thedd.model.character.statistics.StatValues;
import thedd.model.character.statistics.StatValuesImpl;
import thedd.model.character.statistics.Statistic;
import thedd.model.combat.action.Action;
import thedd.model.combat.action.effect.ActionEffect;
import thedd.model.combat.actor.automatic.AbstractAutomaticActor;
import thedd.model.combat.modifier.DamageModifier;
import thedd.model.combat.modifier.HitChanceModifier;
import thedd.model.combat.modifier.Modifier;
import thedd.model.combat.modifier.ModifierActivation;
import thedd.model.combat.modifier.StatBasedModifier;
import thedd.model.combat.requirements.tags.EffectTagsRequirement;
import thedd.model.combat.requirements.tags.TagRequirement;
import thedd.model.combat.requirements.tags.TagRequirementType;
import thedd.model.combat.tag.EffectTag;
import thedd.model.combat.tag.Tag;
import thedd.model.item.Item;
import thedd.model.item.equipableitem.EquipableItem;
import thedd.model.item.equipableitem.EquipableItemType;

/**
 * Implementation of {@link thedd.model.character.BasicCharacter}.
 */
public abstract class BasicCharacterImpl extends AbstractAutomaticActor implements BasicCharacter {

    private final EnumMap<Statistic, StatValues> stat;
    private final Inventory inventory;
    private final List<EquipableItem> equipment;
    private int hash;
    // Modifier constants
    private static final double COS_POISON_RESISTANCE_PERC = -0.05;
    private static final double STR_DAMAGE_ATK_PERC = 0.05;
    private static final double DEX_HIT_CHANCE_ATK_PERC = 0.1;
    private static final double DEX_HIT_CHANCE_DEF_PERC = -0.1;
    private static final double COS_DMG_RES_DEF = -0.02;
    /**
     * BasicCharacterImpl's constructor.
     * 
     * @param name            the name of the character.
     * @param isInPlayerParty true if the actor is part of the player's party
     */
    protected BasicCharacterImpl(final String name, final boolean isInPlayerParty) {
        super(name, isInPlayerParty);
        this.stat = new EnumMap<>(Statistic.class);
        initStat();
        this.inventory = new InventoryImpl();
        this.equipment = new ArrayList<>();
        setCommonStatBasedModifiers();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAlive() {
        return stat.get(Statistic.HEALTH_POINT).getActual() > 0;
    }

    @Override
    public final StatValues getStat(final Statistic stat) {
        Objects.requireNonNull(stat);
        return this.stat.get(stat);
    }

    @Override
    public final EnumMap<Statistic, StatValues> getAllStat() {
        final EnumMap<Statistic, StatValues> ret = new EnumMap<>(Statistic.class);
        stat.entrySet().forEach(el -> {
            ret.put(el.getKey(), el.getValue());
        }); // done a defense copy
        return ret;
    }

    @Override
    public final boolean equipItem(final Item item) {
        Objects.requireNonNull(item);
        if (item.isEquipable()) {
            final EquipableItem equipItem = (EquipableItem) item;
            if (isItemEquipableOnEquipment(equipItem)) {
                inventory.removeItem(item);
                equipItem.onEquip(this);
                equipment.add(equipItem);
                return true;
            }
        }
        return false;
    }

    @Override
    public final boolean unequipItem(final Item item) {
        Objects.requireNonNull(item);
        final int index = equipment.indexOf(item);
        if (index == -1) {
            return false;
        }
        equipment.get(index).onUnequip(this);
        inventory.addItem(equipment.remove(index));
        return true;
    }

    @Override
    public final List<EquipableItem> getEquippedItems() {
        return Collections.unmodifiableList(equipment);
    }

    @Override
    public final Inventory getInventory() {
        return inventory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPriority() {
        return stat.get(Statistic.AGILITY).getActual();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Name: " + this.getName() + " - Stat: " + stat + "\nEquipment: " + this.equipment + " - Inventory: "
                + inventory.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        if (hash == 0) {
            hash = equipment.hashCode() ^ inventory.hashCode() ^ stat.hashCode() ^ super.hashCode();
        }
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (super.equals(obj) && obj instanceof BasicCharacterImpl) {
            final BasicCharacterImpl other = (BasicCharacterImpl) obj;
            return inventory.equals(other.getInventory()) && stat.equals(other.getAllStat())
                    && getEquippedItems().equals(other.getEquippedItems());
        }
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isItemEquipableOnEquipment(final EquipableItem item) {
        if (item.getType().isWeapon()) {
            if (this.getEquippedItems().stream().noneMatch(it -> it.getType() == EquipableItemType.TWO_HANDED)) {
                final int oneHandWeapons = (int) this.getEquippedItems().stream()
                        .filter(it -> it.getType() == EquipableItemType.ONE_HANDED).count();
                return (oneHandWeapons == 0 || (oneHandWeapons == 1 && item.getType() == EquipableItemType.ONE_HANDED));
            }
            return false;
        } else if (item.getType() == EquipableItemType.RING) {
            return ((int) this.getEquippedItems().stream().filter(it -> it.getType() == EquipableItemType.RING)
                    .count() < EquipableItemType.getMaxNumOfRings());
        } else {
            return this.getEquippedItems().stream().noneMatch(it -> it.getType() == item.getType());
        }
    }

    private void setCommonStatBasedModifiers() {
        final ModifierActivation offensive = ModifierActivation.ACTIVE_ON_ATTACK;
        final ModifierActivation defensive = ModifierActivation.ACTIVE_ON_DEFENCE;
        List<Tag> requiredTags = new ArrayList<Tag>();
        List<Tag> allowedTags = new ArrayList<Tag>();
        // Resistance to poison per CONSTITUTION point
        final Modifier<ActionEffect> cosPoisonResistance = new StatBasedModifier<>(Statistic.CONSTITUTION, this,
                new DamageModifier(COS_POISON_RESISTANCE_PERC, true, true, defensive));
        requiredTags = Arrays.asList(EffectTag.POISON_DAMAGE);
        cosPoisonResistance.addRequirement(new TagRequirement<>(false, TagRequirementType.REQUIRED, requiredTags));
        // Bonus to damage per STRENGTH point
        final Modifier<ActionEffect> strDamage = new StatBasedModifier<>(Statistic.STRENGTH, this,
                new DamageModifier(STR_DAMAGE_ATK_PERC, true, true, offensive));
        requiredTags = Arrays.asList(EffectTag.NORMAL_DAMAGE);
        strDamage.addRequirement(new TagRequirement<>(false, TagRequirementType.REQUIRED, requiredTags));
        // Bonus to hit chance per AGILITY point
        final Modifier<Action> dexHitChance = new StatBasedModifier<>(Statistic.AGILITY, this,
                new HitChanceModifier(DEX_HIT_CHANCE_ATK_PERC, true, offensive));
        requiredTags = Arrays.asList(EffectTag.NORMAL_DAMAGE);
        dexHitChance.addRequirement(new EffectTagsRequirement<>(false, TagRequirementType.REQUIRED, requiredTags));
        // Bonus to chances of being missed by a physical attack per AGILITY point
        final Modifier<Action> dexMissChance = new StatBasedModifier<>(Statistic.AGILITY, this,
                new HitChanceModifier(DEX_HIT_CHANCE_DEF_PERC, true, defensive));
        requiredTags = Arrays.asList(EffectTag.NORMAL_DAMAGE);
        dexMissChance.addRequirement(new EffectTagsRequirement<>(false, TagRequirementType.REQUIRED, requiredTags));
        // Resistance to physical damage per CONSTITUTION point
        final Modifier<ActionEffect> cosDmgResistance = new StatBasedModifier<>(Statistic.CONSTITUTION, this,
                new DamageModifier(COS_DMG_RES_DEF, true, true, defensive));
        allowedTags = Arrays.asList(EffectTag.NORMAL_DAMAGE);
        cosDmgResistance.addRequirement(new TagRequirement<>(false, TagRequirementType.ALLOWED, allowedTags));
        addActionModifier(dexMissChance, true);
        addActionModifier(dexHitChance, true);
        addEffectModifier(cosDmgResistance, true);
        addEffectModifier(strDamage, true);
        addEffectModifier(cosPoisonResistance, true);
    }

    private void initStat() {
        this.stat.put(Statistic.HEALTH_POINT, StatValuesImpl.buildWithMax(this.getHealthPointBaseValue()));
        this.stat.put(Statistic.AGILITY, StatValuesImpl.buildWithoutMax(this.getAgilityStatBaseValue()));
        this.stat.put(Statistic.CONSTITUTION, StatValuesImpl.buildWithoutMax(this.getConstitutionStatBaseValue()));
        this.stat.put(Statistic.STRENGTH, StatValuesImpl.buildWithoutMax(this.getStrengthStatBaseValue()));
    }

    /**
     * Return the specific Health Point value.
     * 
     * @return a int value.
     */
    public abstract int getHealthPointBaseValue();

    /**
     * Return the specific Agility value.
     * 
     * @return a int value.
     */
    public abstract int getAgilityStatBaseValue();

    /**
     * Return the specific Constitution value.
     * 
     * @return a int value.
     */
    public abstract int getConstitutionStatBaseValue();

    /**
     * Return the specific Strength value.
     * 
     * @return a int value.
     */
    public abstract int getStrengthStatBaseValue();
}
