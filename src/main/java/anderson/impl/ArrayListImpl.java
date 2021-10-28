package anderson.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import anderson.api.List;
import anderson.api.ArraySort;

public class ArrayListImpl<E> implements List<E> {
    private final int DEFAULT_CAPACITY = 10;
    private final int DEFAULT_SIZE = 0;
    private final double DEFAULT_LOAD_FACTOR = 0.75;
    private final String ARRAY_CANNOT_CONTAIN_NULL_VALUES_EXCEPTION_MESSAGE = "Array cannot contain null values";
    private final String METHOD_WITH_NEGATIVE_INDEX_EXCEPTION_MESSAGE = "Cannot call method with negative index; index = %d";
    private final String METHOD_WITH_INDEX_GREATER_THAN_CURRENT_SIZE_EXCEPTION_MESSAGE = "Cannot call method with index greater than current size; size = %d";

    /**
     * Size describes how many elements are already stored in the list.
     */
    private int size = DEFAULT_SIZE;
    /**
     * Capacity determines the overall number of elements a list can store.
     * Capacity is enlarged each time a threshold is crossed.
     */
    private int capacity = DEFAULT_CAPACITY;
    /**
     *
     * Load factor is used to calculate threshold. In current version cannot be set.
     */
    private double loadFactor = DEFAULT_LOAD_FACTOR;

    /**
     * Threshold determines at which point list's capacity should be enlarged.
     * Calculated within constructor and growCapacity method.
     */
    private int threshold;

    /**
     * Array for storing elements
     */
    private E[] array;

    private ArraySort<E> sortingAlgorithm;

    /**
     *  Default constructor which will init arraylist with default capacity.
     *
     * @param <E>   type of element which will be stored in a list.
     */
    public <E>ArrayListImpl() {
        this.capacity = DEFAULT_CAPACITY;
        this.array = getEmptyArray(this.capacity);
        this.threshold = calculateThreshold();
        this.sortingAlgorithm = new QuickArraySortImpl<>();
    }

    /**
     *  Constructor which will init arraylist with user-specified capacity.
     *
     * @param capacity      capacity of an arraylist.
     * @param <E>           type of element which will be stored in a list.
     */
    public <E>ArrayListImpl(int capacity) {
        this.capacity = capacity;
        this.array = getEmptyArray(capacity);
        this.threshold = calculateThreshold();
        this.sortingAlgorithm = new QuickArraySortImpl<>();
    }

    public int getCapacity() {
        return capacity;
    }

    /**
     * Creates an empty array of specified capacity. Used internally.
     *
     * @param capacity      user-specified capacity.
     * @return              new empty array of length = capacity.
     */
    private E[] getEmptyArray(int capacity) {
        return (E[]) new Object[capacity];
    }

    /**
     * Calculates threshold which is used to determine when to enlarge capacity.
     *
     * @return      integer threshold.
     */
    private int calculateThreshold() {
        return (int) (this.capacity * this.loadFactor);
    }

    /**
     * Calculates new capacity.
     *
     * Copies all the elements to a bigger arraylist and recalculates threshold.
     */
    private void growCapacity() {
        int newCapacity = capacity + DEFAULT_CAPACITY;
        E[] newArray = getEmptyArray(newCapacity);

        System.arraycopy(array, 0, newArray, 0, this.array.length);

        array = newArray;
        threshold = calculateThreshold();
    }

    @Override
    public boolean add(E element) {
        if (element == null) {
            throw new IllegalArgumentException(ARRAY_CANNOT_CONTAIN_NULL_VALUES_EXCEPTION_MESSAGE);
        }

        if (size == threshold) {
            growCapacity();
        }

        array[size] = element;
        size += 1;

        return true;
    }

    @Override
    public void add(int index, E element) {
        if (element == null) {
            throw new IllegalArgumentException(ARRAY_CANNOT_CONTAIN_NULL_VALUES_EXCEPTION_MESSAGE);
        }
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format(METHOD_WITH_NEGATIVE_INDEX_EXCEPTION_MESSAGE, index));
        } else if (index >= size) {
            throw new IndexOutOfBoundsException(String.format(METHOD_WITH_INDEX_GREATER_THAN_CURRENT_SIZE_EXCEPTION_MESSAGE, index));
        }

        E[] left = Arrays.copyOfRange(array, 0, index);
        E[] middle = (E[]) new Object[] { element };
        E[] right = Arrays.copyOfRange(array, index, size);

        array = (E[]) Stream.of(left, middle , right).flatMap(chunk -> Stream.of(chunk)).toArray();
        size += 1;
    }

    @Override
    public void clear() {
        array = getEmptyArray(capacity);
        size = DEFAULT_SIZE;
    }

    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new IllegalArgumentException(ARRAY_CANNOT_CONTAIN_NULL_VALUES_EXCEPTION_MESSAGE);
        }

        boolean isPresent = Arrays.stream(array).filter(Objects::nonNull).anyMatch(e -> e.equals(element));

        return isPresent;
    }

    @Override
    public E get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format(METHOD_WITH_NEGATIVE_INDEX_EXCEPTION_MESSAGE, index));
        } else if (index >= size) {
            throw new IndexOutOfBoundsException(String.format(METHOD_WITH_INDEX_GREATER_THAN_CURRENT_SIZE_EXCEPTION_MESSAGE, index));
        }

        return this.array[index];
    }

    @Override
    public int indexOf(E element) {
        if (element == null) {
            throw new IllegalArgumentException(ARRAY_CANNOT_CONTAIN_NULL_VALUES_EXCEPTION_MESSAGE);
        }

        OptionalInt indexResult = IntStream.range(0, size).filter(i -> element.equals(get(i))).findFirst();

        return indexResult.orElse(-1);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean remove(E element) {
        int index = indexOf(element);

        if (index != -1) {
            remove(index);

            return true;
        }

        return false;
    }

    @Override
    public E remove(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format("Cannot call method with negative index; index = %d", index));
        } else if (index >= size) {
            throw new IndexOutOfBoundsException(String.format("Cannot call method with index greater than current size; size = %d", index));
        }

        E element = array[index];

        E[] left = Arrays.copyOfRange(array, 0, index);
        E[] right = Arrays.copyOfRange(array, index, size);

        array = (E[]) Stream.of(left, right).flatMap(chunk -> Stream.of(chunk)).toArray();
        size -= 1;

        return element;
    }

    @Override
    public void sort(Comparator<E> comparator) {
        E[] sorted = sortingAlgorithm.sort(Arrays.copyOf(array, size()), comparator);

        array = sorted;

        growCapacity();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size());
    }
}