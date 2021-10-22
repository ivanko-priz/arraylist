package anderson.impl;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import anderson.api.ArrayList;
import anderson.api.Sort;

public class ArrayListImpl<E> implements ArrayList<E> {
    private final int DEFAULT_CAPACITY = 10;
    private final int DEFAULT_SIZE = 0;
    private final double DEFAULT_LOAD_FACTOR = 0.75;

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

    private Sort<E> sortingAlgorithm;

    /**
     *  Default constructor which will init arraylist with default capacity.
     *
     * @param <E>   type of element which will be stored in a list.
     */
    public <E>ArrayListImpl() {
        this.capacity = this.DEFAULT_CAPACITY;
        this.array = this.getEmptyArray(this.capacity);
        this.threshold = this.calculateThreshold();
        this.sortingAlgorithm = new QuickSortImpl<>();
    }

    /**
     *  Constructor which will init arraylist with user-specified capacity.
     *
     * @param capacity      capacity of an arraylist.
     * @param <E>           type of element which will be stored in a list.
     */
    public <E>ArrayListImpl(int capacity) {
        this.capacity = capacity;
        this.array = this.getEmptyArray(this.capacity);
        this.threshold = this.calculateThreshold();
        this.sortingAlgorithm = new QuickSortImpl<>();
    }

    public int getCapacity() {
        return this.capacity;
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
        int newCapacity = this.capacity + this.DEFAULT_CAPACITY;
        E[] newArray = getEmptyArray(newCapacity);

        System.arraycopy(this.array, 0, newArray, 0, this.array.length);

        this.array = newArray;
        this.threshold = this.calculateThreshold();
    }

    @Override
    public boolean add(E element) {
        if (this.size == this.threshold) {
            this.growCapacity();
        }

        this.array[size] = element;
        size += 1;

        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format("Cannot call method with negative index; index = %d", index));
        } else if (index >= this.size) {
            throw new IndexOutOfBoundsException(String.format("Cannot call method with index greater than current size; size = %d", index));
        }

        E[] left = Arrays.copyOfRange(this.array, 0, index);
        E[] middle = (E[]) new Object[] { element };
        E[] right = Arrays.copyOfRange(this.array, index, this.size);

        this.array = (E[]) Stream.of(left, middle , right).flatMap(chunk -> Stream.of(chunk)).toArray();
        this.size += 1;
    }

    @Override
    public void clear() {
        this.array = this.getEmptyArray(this.capacity);
        this.size = this.DEFAULT_SIZE;
    }

    @Override
    public boolean contains(E element) {
        boolean isPresent = Arrays.stream(this.array).filter(Objects::nonNull).anyMatch(e -> e.equals(element));

        return isPresent;
    }

    @Override
    public E get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format("Cannot call method with negative index; index = %d", index));
        } else if (index >= this.size) {
            throw new IndexOutOfBoundsException(String.format("Cannot call method with index greater than current size; size = %d", index));
        }

        return this.array[index];
    }

    @Override
    public int indexOf(E element) {
        OptionalInt indexResult = IntStream.range(0, this.size).filter(i -> element.equals(this.get(i))).findFirst();

        return indexResult.orElse(-1);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean remove(E element) {
        int index = this.indexOf(element);

        if (index != -1) {
            this.remove(index);

            return true;
        }

        return false;
    }

    @Override
    public E remove(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format("Cannot call method with negative index; index = %d", index));
        } else if (index >= this.size) {
            throw new IndexOutOfBoundsException(String.format("Cannot call method with index greater than current size; size = %d", index));
        }

        E element = this.array[index];

        E[] left = Arrays.copyOfRange(this.array, 0, index);
        E[] right = Arrays.copyOfRange(this.array, index, this.size);

        this.array = (E[]) Stream.of(left, right).flatMap(chunk -> Stream.of(chunk)).toArray();
        this.size -= 1;

        return element;
    }

    @Override
    public void sort(Comparator<E> comparator) {
        this.sortingAlgorithm.sort(this.array, comparator);
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.array, this.size());
    }
}