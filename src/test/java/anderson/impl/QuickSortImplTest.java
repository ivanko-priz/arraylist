package anderson.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringStartsWith.startsWith;

import anderson.api.Sort;

public class QuickSortImplTest {
    @Test
    public void shouldSortArrayOfComparables() {
        final Integer[] EXPECTED = { 0, 0, 5, 8, 9, 12, 34, 41 };

        Integer[] array = { 5, 8, 12, 34, 41, 0, 0, 9};

        Sort<Integer> sort = new QuickSortImpl<>();

        assertArrayEquals(EXPECTED, sort.sort(array));
    }

    @Test
    public void shouldSortUsingCustomComparator() {
        final Integer[] EXPECTED = { 41, 34, 12, 9, 8, 5, 0, 0 };

        Integer[] array = { 5, 8, 12, 34, 41, 0, 0, 9};

        Sort<Integer> sort = new QuickSortImpl<>();

        assertArrayEquals(EXPECTED, sort.sort(array, (a, b) -> a - b));
    }

    @Test
    void shouldSortUsingCustomComparatorForNonNumbericTypes() {
        final String[] EXPECTED = { "I", "don't", "like", "the", "zoo" };

        String[] array = { "zoo", "the", "I", "don't", "like",};

        Sort<String> sort = new QuickSortImpl<>();

        assertArrayEquals(EXPECTED, sort.sort(array, (a, b) -> b.compareTo(a)));
    }
}
