package org.interpreter.model.value;

import org.interpreter.model.type.BoolType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoolValueTest {

    @Test
    public void getTypeTest(){
        assertEquals(new BoolType(), new BoolValue(false).getType());
    }

    @Test
    public void toStringTest(){
        assertEquals("false", new BoolValue(false).toString());
    }

}