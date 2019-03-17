package model.item;

import java.util.Random;

/**
 * 
 *
 */
public enum ItemRarityImpl implements ItemRarity {
    /**
     * The least rare. It has the highest weight.
     */
    COMMON("Common", 6),
    /**
     * The median rare. It has an average weight.
     */
    UNCOMMON("Uncommon", 3),
    /**
     * The rarest. It has the lowest weight.
     */
    RARE("Rare", 3);

    private final int baseWeight;
    private final String literal;

    ItemRarityImpl(final String literal, final int baseWeight) {
        this.baseWeight = baseWeight;
        this.literal = literal;
    }

    @Override
    public String getLiteral() {
        return literal;
    }

    @Override
    public int getBaseWeight() {
        return baseWeight;
    }

    /**
     * 
     * @return
     *  a random rarity from the values of this enumeration
     */
    public static ItemRarity getRandomRarity() {
       final Random r = new Random();
       return ItemRarityImpl.values()[r.nextInt(ItemRarityImpl.values().length)]; 
    }
}
