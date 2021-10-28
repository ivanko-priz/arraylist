package anderson.impl;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

import anderson.api.List;

public class ArrayListImplTest {
    @Test
    public void shouldInitWithoutError() {
        final int DEFAULT_CAPACITY = 10;
        final int DEFAULT_SIZE = 0;

        List<Integer> list = new ArrayListImpl<>();

        assertEquals(DEFAULT_SIZE, list.size());
        assertEquals(DEFAULT_CAPACITY, list.getCapacity());
    }

    @Test
    public void shouldInitWithProvidedCapacity() {
        final int CAPACITY = 15;

        List<Integer> list = new ArrayListImpl<>(CAPACITY);
    }

    @Test
    public void shouldAddElement() {
        final int EXPECTED_ELEMENT = 5;

        List<Integer> list = new ArrayListImpl<>();

        list.add(5);

        assertEquals(EXPECTED_ELEMENT, 5);
    }

    @Test
    public void shouldThrowWhenNullElementAdded() {
        List<Integer> list = new ArrayListImpl<>();

        assertThrows(IllegalArgumentException.class, () -> {
            list.add(null);
        });
    }

    @Test
    public void shouldThrowWhenNullElementAddedAtPosition() {
        List<Integer> list = new ArrayListImpl<>();

        assertThrows(IllegalArgumentException.class, () -> {
            list.add(0, null);
        });
    }

    @Test
    public void shouldIncrementSizeAfterAddingElement() {
        final int EXPECTED_LIST_SIZE = 1;

        List<Integer> list = new ArrayListImpl<>();

        list.add(5);

        assertEquals(EXPECTED_LIST_SIZE, 1);
    }

    @Test
    public void shouldKeepTrackOfMultipleElements() {
        final int EXPECTED_SIZE = 5;

        List<Integer> list = new ArrayListImpl<>();

        list.add(5);
        list.add(7);
        list.add(3);
        list.add(10);
        list.add(13);

        assertEquals(EXPECTED_SIZE, list.size());
    }

    @Test
    public void shouldPreserveOrderOfAddedElements() {
        final Integer[] EXPECTED_ARRAY = { 5, 7, 3, 10, 13 };

        List<Integer> list = new ArrayListImpl<>();

        list.add(5);
        list.add(7);
        list.add(3);
        list.add(10);
        list.add(13);

        assertArrayEquals(EXPECTED_ARRAY, list.toArray());
    }

    @Test
    public void shouldAddElementAtSpecifiedPosition() {
        final int EXPECTED_VALUE = 100;
        final int EXPECTED_SIZE = 6;

        List<Integer> list = new ArrayListImpl<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list.add(2, 100);

        assertEquals(EXPECTED_VALUE, (long) list.get(2));
        assertEquals(EXPECTED_SIZE, list.size());
    }

    @Test
    public void shouldClearArrayList() {
        final int EXPECTED_SIZE = 0;

        List<Integer> list = new ArrayListImpl<>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        list.clear();

        assertEquals(EXPECTED_SIZE, list.size());
        assertEquals(EXPECTED_SIZE, list.toArray().length);
    }

    @Test
    public void shouldReturnTrueIfContainsElement() {
        List<Integer> list = new ArrayListImpl<>();

        list.add(5);
        list.add(12);
        list.add(38);

        assertTrue(list.contains(38));
    }

    @Test
    public void shouldReturnFalseIfNotContainsElemetn() {
        List<Integer> list = new ArrayListImpl<>();

        list.add(5);
        list.add(12);
        list.add(38);

        assertFalse(list.contains(1000));
    }

    @Test
    public void shouldGetElementByIndex() {
        final int EXPECTED_VALUE = 38;

        List<Integer> list = new ArrayListImpl<>();

        list.add(5);
        list.add(12);
        list.add(38);

        assertEquals(EXPECTED_VALUE, (long) list.get(2));
    }

    @Test
    public void shouldThrowWhenNegativeIndexIsPassedToGet() {
        List<Integer> list = new ArrayListImpl<>();

        assertThrows(IndexOutOfBoundsException.class, () -> {
           list.get(-1);
        });
    }

    @Test
    public void shouldThrowWhenTooLargeIndexIsPassedToGet() {
        List<Integer> list = new ArrayListImpl<>();

        assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(1000);
        });
    }

    @Test
    public void shouldReturnIndexOfPresentElement() {
        final int EXPECTED_INDEX = 2;

        List<Integer> list = new ArrayListImpl<>();

        list.add(12);
        list.add(43);
        list.add(44);
        list.add(11);

        assertEquals(EXPECTED_INDEX, list.indexOf(44));
    }

    @Test
    public void shouldReturnNegativeWhenAskedForMissingElement() {
        final int EXPECTED_INDEX = -1;

        List<Integer> list = new ArrayListImpl<>();

        list.add(12);
        list.add(43);
        list.add(44);
        list.add(11);

        assertEquals(EXPECTED_INDEX, list.indexOf(100));
    }

    @Test
    public void shouldRemoveElementByValue() {
        final int EXPECTED_SIZE = 3;

        List<Integer> list = new ArrayListImpl<>();

        list.add(12);
        list.add(43);
        list.add(44);
        list.add(11);

        Integer element = 43;

        assertTrue(list.remove(element));
        assertEquals(EXPECTED_SIZE, list.size());
    }

    @Test
    public void shouldReturnFalseWhenRemovingMissingElement() {
        final int EXPECTED_SIZE = 4;

        List<Integer> list = new ArrayListImpl<>();

        list.add(12);
        list.add(43);
        list.add(44);
        list.add(11);

        Integer element = 100;

        assertFalse(list.remove(element));
        assertEquals(EXPECTED_SIZE, list.size());
    }

    @Test
    public void shouldThrowWhenRemovingElementByNegativeIndex() {
        final String NEGATIVE_INDEX_ERROR_MESSAGE = "Cannot call method with negative index";

        List<Integer> list = new ArrayListImpl<>();

        Exception e = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        });

        assertThat(e.getMessage(), startsWith(NEGATIVE_INDEX_ERROR_MESSAGE));
    }

    @Test
    public void shouldThrowWhenRemovingElementByTooLargeIndex() {
        final String TOO_LARGE_INDEX_ERROR_MESSAGE = "Cannot call method with index greater than current size";

        List<Integer> list = new ArrayListImpl<>();

        Exception e = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(2341);
        });

        assertThat(e.getMessage(), startsWith(TOO_LARGE_INDEX_ERROR_MESSAGE));
    }

    @Test
    public void shouldRemoveElementById() {
        final int VALUE_TO_BE_REMOVED = 5;

        List<Integer> list = new ArrayListImpl<>();

        list.add(VALUE_TO_BE_REMOVED);

        int value = list.remove(0);
        int size = list.size();

        assertEquals(VALUE_TO_BE_REMOVED, value);
        assertEquals(0, size);
    }

    @Test
    public void shouldSortArrayListInAscOrder() {
        final Integer[] EXPECTED_SORTED_ARRAY = { 2, 3, 4, 5 };

        List<Integer> list = new ArrayListImpl<>();

        list.add(5);
        list.add(2);
        list.add(3);
        list.add(4);

        list.sort((a, b) -> b - a);

        assertArrayEquals(EXPECTED_SORTED_ARRAY, list.toArray());
    }

    @Test
    public void shouldSortArrayListInDescOrder() {
        final Integer[] EXPECTED_SORTED_ARRAY = { 5, 4, 3, 2 };

        List<Integer> list = new ArrayListImpl<>();

        list.add(5);
        list.add(2);
        list.add(3);
        list.add(4);

        list.sort((a, b) -> a - b);

        assertArrayEquals(EXPECTED_SORTED_ARRAY, list.toArray());
    }
}
