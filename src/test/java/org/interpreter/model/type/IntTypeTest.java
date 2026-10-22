package org.interpreter.model.type;

import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntTypeTest {

    @Test
    public void toStringTest(){
        assertEquals("int", new IntType().toString() );
    }

    @Test
    public void defaultValueTest(){
        assertEquals(new IntValue(0), new IntType().defaultValue());
    }

}