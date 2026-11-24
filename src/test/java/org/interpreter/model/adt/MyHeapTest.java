package org.interpreter.model.adt;

import org.interpreter.exceptions.ADTException;
import org.interpreter.model.type.IntType;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.interpreter.model.value.RefValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyHeapTest {

    private MyHeap heap;

    @BeforeEach
    public void setUp(){
        heap = new MyHeap();
    }

    @Test
    public void allocateReturnsConsecutiveAddressesFromOneTest() throws ADTException {
        assertEquals(1, heap.allocate(new IntValue(20)));
        assertEquals(2, heap.allocate(new RefValue(1, new IntType())));
        assertEquals(3, heap.allocate(new IntValue(30)));

        assertEquals(new IntValue(20), heap.get(1));
        assertEquals(new RefValue(1, new IntType()), heap.get(2));
        assertEquals(new IntValue(30), heap.get(3));
    }

    @Test
    public void addressZeroIsNeverDefinedTest(){
        heap.allocate(new IntValue(1));

        assertFalse(heap.isDefined(0));
        assertTrue(heap.isDefined(1));
        assertFalse(heap.isDefined(2));
    }

    @Test
    public void getMissingAddressTestError(){
        assertThrows(ADTException.class, () -> heap.get(0));
        assertThrows(ADTException.class, () -> heap.get(1));
    }

    @Test
    public void updateTest() throws ADTException {
        heap.allocate(new IntValue(20));
        heap.update(1, new IntValue(30));

        assertEquals(new IntValue(30), heap.get(1));
        assertEquals(1, heap.getContent().size());
    }

    @Test
    public void updateMissingAddressTestError(){
        assertThrows(ADTException.class, () -> heap.update(1, new IntValue(30)));
        assertFalse(heap.isDefined(1));
    }

    @Test
    public void getContentIsReadOnlyCopyTest(){
        heap.allocate(new IntValue(20));
        Map<Integer, IValue> content = heap.getContent();

        assertEquals(Map.of(1, new IntValue(20)), content);
        assertThrows(UnsupportedOperationException.class, () -> content.put(2, new IntValue(1)));
        heap.allocate(new IntValue(30));
        assertEquals(1, content.size()); // a copy, not a live view
    }

    @Test
    public void setContentReplacesLocationsButKeepsNextFreeTest() throws ADTException {
        heap.allocate(new IntValue(1));
        heap.allocate(new IntValue(2));
        heap.allocate(new IntValue(3));

        heap.setContent(Map.of(2, new IntValue(2)));   // what the garbage collector does

        assertFalse(heap.isDefined(1));
        assertFalse(heap.isDefined(3));
        assertEquals(new IntValue(2), heap.get(2));
        assertEquals(4, heap.allocate(new IntValue(4)));  // addresses are never reused
    }

    @Test
    public void setContentDoesNotAliasTheGivenMapTest(){
        Map<Integer, IValue> map = new HashMap<>(Map.of(1, new IntValue(1)));
        heap.setContent(map);
        map.clear();

        assertTrue(heap.isDefined(1));
    }

    @Test
    public void toStringTest(){
        heap.allocate(new IntValue(20));

        assertEquals("{1=20}", heap.toString());
    }
}
