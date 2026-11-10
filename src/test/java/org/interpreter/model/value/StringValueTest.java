package org.interpreter.model.value;

import org.interpreter.model.type.IntType;
import org.interpreter.model.type.StringType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringValueTest {
    @Test
    public void getTypeTest(){
        assertEquals(new StringType(), new StringValue("ana").getType());
    }

    @Test
    public void toStringTest(){
        assertEquals("\"1\"", new StringValue("1").toString());
    }

    @Test
    public void equalsTest(){

        var val1 = new StringValue("3");
        var val2 = new StringValue("3");

        assertEquals(val1, val2);
        assertTrue(val1.equals(val2));
    }


    @Test
    public void equalValuesHaveSameHashCodeTest(){
        assertEquals(new StringValue("test.in").hashCode(), new StringValue("test.in").hashCode());
        assertNotEquals(new StringValue("a"), new StringValue("b"));
    }
}
