package anderson.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

import anderson.api.ArrayList;

public class ArrayListImplTest {
    @Test
    public void shouldInitWithoutError() {
        final int DEFAULT_CAPACITY = 10;
        final int DEFAULT_SIZE = 0;

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        assertEquals(DEFAULT_SIZE, arrayList.size());
        assertEquals(DEFAULT_CAPACITY, arrayList.getCapacity());
    }

    @Test
    public void shouldInitWithProvidedCapacity() {
        final int CAPACITY = 15;

        ArrayList<Integer> arrayList = new ArrayListImpl<>(CAPACITY);
    }

    @Test
    public void shouldAddElement() {
        final int EXPECTED_ELEMENT = 5;

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(5);

        assertEquals(EXPECTED_ELEMENT, 5);
    }

    @Test
    public void shouldIncrementSizeAfterAddingElement() {
        final int EXPECTED_LIST_SIZE = 1;

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(5);

        assertEquals(EXPECTED_LIST_SIZE, 1);
    }

    @Test
    public void shouldKeepTrackOfMultipleElements() {
        final int EXPECTED_SIZE = 5;

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(5);
        arrayList.add(7);
        arrayList.add(3);
        arrayList.add(10);
        arrayList.add(13);

        assertEquals(EXPECTED_SIZE, arrayList.size());
    }

    @Test
    public void shouldPreserveOrderOfAddedElements() {
        final Integer[] EXPECTED_ARRAY = { 5, 7, 3, 10, 13 };

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(5);
        arrayList.add(7);
        arrayList.add(3);
        arrayList.add(10);
        arrayList.add(13);

        assertArrayEquals(EXPECTED_ARRAY, arrayList.toArray());
    }

    @Test
    public void shouldAddElementAtSpecifiedPosition() {
        final int EXPECTED_VALUE = 100;
        final int EXPECTED_SIZE = 6;

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);

        arrayList.add(2, 100);

        assertEquals(EXPECTED_VALUE, (long)arrayList.get(2));
        assertEquals(EXPECTED_SIZE, arrayList.size());
    }

    @Test
    public void shouldClearArrayList() {
        final int EXPECTED_SIZE = 0;

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);

        arrayList.clear();

        assertEquals(EXPECTED_SIZE, arrayList.size());
        assertEquals(EXPECTED_SIZE, arrayList.toArray().length);
    }

    @Test
    public void shouldReturnTrueIfContainsElement() {
        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(5);
        arrayList.add(12);
        arrayList.add(38);

        assertTrue(arrayList.contains(38));
    }

    @Test
    public void shouldReturnFalseIfNotContainsElemetn() {
        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(5);
        arrayList.add(12);
        arrayList.add(38);

        assertFalse(arrayList.contains(1000));
    }

    @Test
    public void shouldGetElementByIndex() {
        final int EXPECTED_VALUE = 38;

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(5);
        arrayList.add(12);
        arrayList.add(38);

        assertEquals(EXPECTED_VALUE, (long)arrayList.get(2));
    }

    @Test
    public void shouldThrowWhenNegativeIndexIsPassedToGet() {
        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        assertThrows(IndexOutOfBoundsException.class, () -> {
           arrayList.get(-1);
        });
    }

    @Test
    public void shouldThrowWhenTooLargeIndexIsPassedToGet() {
        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            arrayList.get(1000);
        });
    }

    @Test
    public void shouldReturnIndexOfPresentElement() {
        final int EXPECTED_INDEX = 2;

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(12);
        arrayList.add(43);
        arrayList.add(44);
        arrayList.add(11);

        assertEquals(EXPECTED_INDEX, arrayList.indexOf(44));
    }

    @Test
    public void shouldReturnNegativeWhenAskedForMissingElement() {
        final int EXPECTED_INDEX = -1;

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(12);
        arrayList.add(43);
        arrayList.add(44);
        arrayList.add(11);

        assertEquals(EXPECTED_INDEX, arrayList.indexOf(100));
    }

    @Test
    public void shouldRemoveElementByValue() {
        final int EXPECTED_SIZE = 3;

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(12);
        arrayList.add(43);
        arrayList.add(44);
        arrayList.add(11);

        Integer element = 43;

        assertTrue(arrayList.remove(element));
        assertEquals(EXPECTED_SIZE, arrayList.size());
    }

    @Test
    public void shouldReturnFalseWhenRemovingMissingElement() {
        final int EXPECTED_SIZE = 4;

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(12);
        arrayList.add(43);
        arrayList.add(44);
        arrayList.add(11);

        Integer element = 100;

        assertFalse(arrayList.remove(element));
        assertEquals(EXPECTED_SIZE, arrayList.size());
    }

    @Test
    public void shouldThrowWhenRemovingElementByNegativeIndex() {
        final String NEGATIVE_INDEX_ERROR_MESSAGE = "Cannot call method with negative index";

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        Exception e = assertThrows(IndexOutOfBoundsException.class, () -> {
            arrayList.remove(-1);
        });

        assertThat(e.getMessage(), startsWith(NEGATIVE_INDEX_ERROR_MESSAGE));
    }

    @Test
    public void shouldThrowWhenRemovingElementByTooLargeIndex() {
        final String TOO_LARGE_INDEX_ERROR_MESSAGE = "Cannot call method with index greater than current size";

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        Exception e = assertThrows(IndexOutOfBoundsException.class, () -> {
            arrayList.remove(2341);
        });

        assertThat(e.getMessage(), startsWith(TOO_LARGE_INDEX_ERROR_MESSAGE));
    }

    @Test
    public void shouldRemoveElementById() {
        final int VALUE_TO_BE_REMOVED = 5;

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(VALUE_TO_BE_REMOVED);

        int value = arrayList.remove(0);
        int size = arrayList.size();

        assertEquals(VALUE_TO_BE_REMOVED, value);
        assertEquals(0, size);
    }

    @Test
    public void shouldSortArrayListInAscOrder() {
        final Integer[] EXPECTED_SORTED_ARRAY = { 2, 3, 4, 5 };

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(5);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);

        arrayList.sort((a, b) -> b - a);

        assertArrayEquals(EXPECTED_SORTED_ARRAY, arrayList.toArray());
    }

    @Test
    public void shouldSortArrayListInDescOrder() {
        final Integer[] EXPECTED_SORTED_ARRAY = { 5, 4, 3, 2 };

        ArrayList<Integer> arrayList = new ArrayListImpl<>();

        arrayList.add(5);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);

        arrayList.sort((a, b) -> a - b);

        assertArrayEquals(EXPECTED_SORTED_ARRAY, arrayList.toArray());
    }
}
