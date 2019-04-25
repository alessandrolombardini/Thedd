package thedd.utils.randomcollections.weighteditem;

import java.util.Objects;

/**
* An item composed of an object of type E and an associated weight.
* @param <E> the type of the item
*/
public final class WeightedItemImpl<E> implements WeightedItem<E> {
        private double weight;
        private final E item;

        /**
         * 
         * @param item the item to be stored
         * @param weight the weight associated with the item
         */
        public WeightedItemImpl(final E item, final double weight) {
            this.item = item;
            this.weight = weight;
        }

        /**
         * {@inheritDoc} 
         */
        @Override
        public double getWeight() {
            return weight;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public E getItem() {
            return item;
        }

        @Override
        public boolean equals(final Object other) {
            if (other == this) {
                return true;
            }
            if (!(other instanceof WeightedItemImpl)) {
                return false;
            } else {
                return this.item.equals(((WeightedItemImpl<?>) other).item);
            }
        }
        @Override
        public int hashCode() {
            return Objects.hashCode(item);
        }

        @Override
        public void setWeight(final double weight) {
            this.weight = weight;
        }
    }

