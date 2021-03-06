package anderson.api;

import java.util.Comparator;

public interface List<E> {
    /**
     * Adds non-null element to the end of the list.
     *
     * @param   element element to be inserted.
     * @return  true if element is inserted.
     */
    boolean add(E element);

    /**
     * Adds non-null element at a specified position within the list's capacity.
     *
     * @param index     position at which an element to be inserted.
     * @param element   element to be inserted.
     */
    void add(int index, E element);

    /**
     * Sets list's size to 0 purging all the elements.
     * Does not change capacity.
     */
    void clear();

    /**
     *  Checks for presence of an element of type E in the list.
     *
     * @param element   element to be checked for presence.
     * @return          true if element is found, otherwise - false.
     */
    boolean contains(E element);

    /**
     *  Gets element by its id.
     *
     * @param index     position of an element to be retrieved.
     * @return          element at given position.
     */
    E get(int index);

    /**
     * Retrieves index (a positive number) of an element if element exists. In case of multiple elements should.
     * return position of a first matched element. Should return -1 if element is absent.
     *
     * @param element   element to be searched for.
     * @return          position as a positive integer if element is present and -1 otherwise.
     */
    int indexOf(E element);

    /**
     *
     * @return      number of elements present in the list.
     */
    int size();

    /**
     * Removes element from the list. Should return false if element is absent and therefore can't be removed and
     * true otherwise.
     *
     * @param element   element to be removed.
     * @return          true if element is removed and false otherwise.
     */
    boolean remove(E element);

    /**
     * Removes element from the list by its index. Should remove and return element of type E if element under such index exists, otherwise - null.
     *
     * @param index     position of an element to be removed.
     * @return          removed element.
     */
    E remove(int index);

    /**
     * Sorts an array using Comparable<E>.
     *
     * @param comparator    comparator which will be used to sort elements.
     */
    void sort(Comparator<E> comparator);

    /**
     * Returns capacity of a list.
     *
     * @return      total number of elements a list can store.
     */
    int getCapacity();

    /**
     * Returns array of elements
     *
     * @return      array E[] with array.length = LinkedList.size()
     */
    Object[] toArray();
}