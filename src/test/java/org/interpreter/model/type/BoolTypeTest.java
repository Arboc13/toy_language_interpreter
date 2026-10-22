package org.interpreter.model.type;

import org.interpreter.model.value.BoolValue;
import org.interpreter.model.value.IntValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoolTypeTest {

    @Test
    public void toStringTest(){
        assertEquals("bool", new BoolType().toString() );
    }

    @Test
    public void defaultValueTest(){
        assertEquals(new BoolValue(false), new BoolType().defaultValue());
    }

}