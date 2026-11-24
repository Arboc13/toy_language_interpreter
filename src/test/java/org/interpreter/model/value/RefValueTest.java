package org.interpreter.model.value;

import org.interpreter.model.type.IntType;
import org.interpreter.model.type.RefType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefValueTest {

    @Test
    public void getTypeTest(){
        assertEquals(new RefType(new IntType()), new RefValue(1, new IntType()).getType());
        assertEquals(new RefType(new RefType(new IntType())),
                new RefValue(10, new RefType(new IntType())).getType());
    }

    @Test
    public void equalsTest(){
        assertEquals(new RefValue(1, new IntType()), new RefValue(1, new IntType()));
        assertNotEquals(new RefValue(2, new IntType()), new RefValue(1, new IntType()));
        assertNotEquals(new RefValue(1, new RefType(new IntType())), new RefValue(1, new IntType()));
    }

    @Test
    public void toStringTest(){
        assertEquals("(1, int)", new RefValue(1, new IntType()).toString());
        assertEquals("(2, Ref(int))", new RefValue(2, new RefType(new IntType())).toString());
    }
}
