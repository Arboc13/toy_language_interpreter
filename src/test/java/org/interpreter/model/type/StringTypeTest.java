package org.interpreter.model.type;

import org.interpreter.model.value.IntValue;
import org.interpreter.model.value.StringValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringTypeTest {
    @Test
    public void toStringTest(){
        assertEquals("text", new IntType().toString() );
    }

    @Test
    public void defaultValueTest(){
        assertEquals(new StringValue(" "), new StringType().defaultValue());
    }

}