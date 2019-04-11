package thedd.utils;

import java.util.Random;

import thedd.utils.randomcollections.RandomCollection;
import thedd.utils.randomcollections.list.RandomListImpl;


/**
 * This class contains some usefull method to get random number.
 *
 */
public final class RandomUtils {


    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private RandomUtils() { }

    /**
     * This method allows to get random boolean with weigth.
     * 
     * @param trueWeight represent the weight of true
     * @param falseWeight represent the weight of false
     * @return a random boolean
     * @throws IllegalArgumentExeption if al least one of two weights is less than 0
     */
    public static boolean getRandomWeightedBoolean(final double trueWeight, final double falseWeight) {
        if (trueWeight < 0 || falseWeight < 0) {
            throw new IllegalArgumentException();
        }
        final RandomCollection<Boolean> randomCollection = new RandomListImpl<Boolean>();
        randomCollection.add(true, trueWeight);
        randomCollection.add(false, falseWeight);
        return randomCollection.getNext();
    }

    /**
     * This method allows to get a random value with a gaussian distribution.
     * 
     * @param mediumVal is the medium value
     * @param var is the varianza
     * @return a random integer
     */
    public static int getRandomIntegerGaussianNumber(final int mediumVal, final int var) {
        return (int) Math.round(RandomUtils.RANDOM.nextGaussian() * Math.sqrt(mediumVal) + var);
    }

    /**
     * This method allows to get a random boolean.
     * 
     * @return boolean
     */
    public static boolean getRandomBoolean() {
        return RandomUtils.RANDOM.nextBoolean();
    }

    /**
     * This method allows to get a random integer between two intergers passed like arguments.
     * 
     * @param min value accepted
     * @param max value accepted
     * @return a random integer
     */
    public static int getRandomIntegerBetweenIntegers(final int min, final int max) {
        return RandomUtils.RANDOM.nextInt(max - min + 1) + min;
    }

    /**
     * This method allows to get a random integer between 0 and maxNumber not excluded.
     * 
     * @param maxNumber is the max value gettable
     * @return an integer
     */
    public static int getRandomInteger(final int maxNumber) {
        return RandomUtils.RANDOM.nextInt(maxNumber + 1);
    }

}
