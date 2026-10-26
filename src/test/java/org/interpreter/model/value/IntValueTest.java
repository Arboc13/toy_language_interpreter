package org.interpreter.model.value;

import org.interpreter.model.type.BoolType;
import org.interpreter.model.type.IntType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntValueTest {

    @Test
    public void getTypeTest(){
        assertEquals(new IntType(), new IntValue(1).getType());
    }

    @Test
    public void toStringTest(){
        assertEquals("1", new IntValue(1).toString());
    }

    @Test
    public void equalsTest(){

        var val1 = new IntValue(3);
        var val2 = new IntValue(3);

        assertEquals(val1, val2);
        assertTrue(val1.equals(val2));
    }
}