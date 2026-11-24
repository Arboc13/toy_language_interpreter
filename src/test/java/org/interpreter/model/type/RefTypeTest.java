package org.interpreter.model.type;

import org.interpreter.model.value.RefValue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RefTypeTest {

    @Test
    public void defaultValueIsNullAddressTest(){
        assertEquals(new RefValue(0, new IntType()), new RefType(new IntType()).defaultValue());
        assertEquals(new RefValue(0, new RefType(new BoolType())),
                new RefType(new RefType(new BoolType())).defaultValue());
    }

    @Test
    public void equalsNestedTypesTest(){
        IType refRefInt = new RefType(new RefType(new IntType()));

        assertEquals(new RefType(new RefType(new IntType())), refRefInt);
        assertEquals(new RefType(new RefType(new IntType())).hashCode(), refRefInt.hashCode());
        assertNotEquals(new RefType(new IntType()), refRefInt);
        assertNotEquals(new RefType(new RefType(new BoolType())), refRefInt);
        assertNotEquals(new IntType(), new RefType(new IntType()));
    }

    @Test
    public void toStringTest(){
        assertEquals("Ref(int)", new RefType(new IntType()).toString());
        assertEquals("Ref(Ref(string))", new RefType(new RefType(new StringType())).toString());
    }
}
