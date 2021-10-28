package anderson.impl;

import java.util.function.BiPredicate;
import java.util.Comparator;

import anderson.api.ArraySort;

public class QuickArraySortImpl<E> implements ArraySort<E> {
    @Override
    public E[] sort(Comparable<E>[] array) {
        E[] elements = (E[]) array;
        BiPredicate<E, E> condition = (element, pivot) -> ((Comparable<E>) element).compareTo(pivot) <= 0;

        quickSort(elements, 0, array.length - 1, condition);

        return elements;
    }

    @Override
    public E[] sort(E[] array, Comparator<E> comparator) {
        BiPredicate<E, E> condition = (element, pivot) -> comparator.compare(element, pivot) >= 0;
        quickSort(array, 0, array.length - 1, condition);

        return array;
    }

    /**
     * Implements Quicksort algorithm.
     *
     * @param array     array to be sorted.
     * @param start     array's start index.
     * @param end       array's end index.
     * @param condition comparison mechanism. Used internally to allow method to be called from overloaded sort methods.
     */
    public void quickSort(E[] array, int start, int end, BiPredicate<E, E> condition) {
        if (start < end) {
            int partitionIndex = partition(array, start, end, condition);

            quickSort(array, start, partitionIndex - 1, condition);
            quickSort(array, partitionIndex + 1, end, condition);
        }
    }

    /**
     * Calculates partition position for Quicksort.
     *
     * @param array     array to be sorted.
     * @param start     array's start index.
     * @param end       array's end index.
     * @param condition comparison mechanism. Used internally to allow method to be called from overloaded sort methods.
     * @return          next pivot upon which a partition will be made.
     */
    private int partition(E[] array, int start, int end, BiPredicate<E, E> condition) {
        E pivot = array[end];
        int i = start - 1;

        for (int j = start; j < end; j++) {
            if (condition.test(array[j], pivot)) {
                i += 1;

                E tmp = array[i];
                array[i] = array[j];
                array[j] = tmp;
            }
        }

        E tmp = array[i + 1];
        array[i + 1] = array[end];
        array[end] = tmp;

        return i += 1;
    }
}
