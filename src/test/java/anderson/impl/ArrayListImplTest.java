package anderson.impl;

import static org.junit.Assert.*;
import org.junit.Test;

import anderson.api.ArrayList;

public class ArrayListImplTest {
    @Test
    public void shouldInitializeWithoutError() {
        ArrayList<Integer> arrayList = new ArrayListImpl<>();
    }
}
