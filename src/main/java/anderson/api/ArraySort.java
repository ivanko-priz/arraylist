package anderson.api;

import java.util.Comparator;

public interface ArraySort<E> {
    /**
     * Sorts an array of comparables elements.
     *
     * @param array     array of comparables to be sorted.
     * @return          array sorted in ascending order.
     */
    E[] sort(Comparable<E>[] array);

    /**
     * Sorts an array of elements using Comparator.
     *
     * @param array         array to be sorted.
     * @param comparator    comparator to use on each element of array.
     * @return              array sorted in ascending order.
     */
    E[] sort(E[] array, Comparator<E> comparator);
}