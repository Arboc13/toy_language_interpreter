package org.interpreter.controller;

import org.interpreter.model.type.IntType;
import org.interpreter.model.type.RefType;
import org.interpreter.model.value.IValue;
import org.interpreter.model.value.IntValue;
import org.interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GarbageCollectorTest {

    private final List<IValue> symTable = List.of(
            new RefValue(3, new IntType()), new RefValue(2, new RefType(new IntType())), new IntValue(5));
    private final Map<Integer, IValue> heap = Map.of(
            1, new IntValue(20), 2, new RefValue(1, new IntType()),
            3, new IntValue(30), 4, new IntValue(7));

    @Test
    public void getAddrFromSymTableTest(){
        assertEquals(Set.of(2, 3), GarbageCollector.getAddrFromSymTable(symTable));
    }

    @Test
    public void unsafeRemovesLocationReachableOnlyThroughTheHeapTest(){
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        Map<Integer, IValue> kept = GarbageCollector.unsafeGarbageCollector(roots, heap);

        assertEquals(Set.of(2, 3), kept.keySet());
    }

    @Test
    public void keepsTheLocationsReachableThroughTheHeapTest(){
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(symTable);
        Map<Integer, IValue> kept = GarbageCollector.safeGarbageCollector(roots, heap);

        assertEquals(Set.of(1, 2, 3), kept.keySet());
        assertEquals(new IntValue(20), kept.get(1));
    }

    @Test
    public void reachableAddressesFollowsLongChainsTest(){
        Map<Integer, IValue> chain = Map.of(
                1, new IntValue(1),
                2, new RefValue(1, new IntType()),
                3, new RefValue(2, new RefType(new IntType())),
                4, new RefValue(3, new RefType(new RefType(new IntType()))),
                5, new IntValue(5));

        assertEquals(Set.of(1, 2, 3, 4), GarbageCollector.reachableAddresses(Set.of(4), chain));
    }

    @Test
    public void reachableAddressesHandlesCyclesTest(){
        Map<Integer, IValue> cycle = Map.of(
                1, new RefValue(2, new IntType()),
                2, new RefValue(1, new IntType()),
                3, new IntValue(3));

        assertEquals(Set.of(1, 2), GarbageCollector.reachableAddresses(Set.of(1), cycle));
    }

    @Test
    public void nullReferenceKeepsNothingTest(){
        Set<Integer> roots = GarbageCollector.getAddrFromSymTable(List.of(new RefValue(0, new IntType())));

        assertTrue(GarbageCollector.safeGarbageCollector(roots, heap).isEmpty());
    }

    @Test
    public void emptySymTableRemovesEverythingTest(){
        assertTrue(GarbageCollector.safeGarbageCollector(Set.of(), heap).isEmpty());
    }
}
