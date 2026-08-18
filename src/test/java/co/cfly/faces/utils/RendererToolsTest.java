package co.cfly.faces.utils;

import org.junit.Assert;
import org.junit.Test;

public class RendererToolsTest {

    @Test
    public void spaceSeparatedString1() {
        String one = "one";
        String two = "two";
        String three = "three";
        Assert.assertEquals("one two three", RendererTools.spaceSeparateStrings(one, two, three));
    }

    @Test
    public void spaceSeparatedString2() {
        String one = "one";
        String two = "two bar";
        String three = "three";
        Assert.assertEquals("one two bar three", RendererTools.spaceSeparateStrings(one, two, three));
    }

    @Test
    public void spaceSeparatedString3() {
        String one = "one";
        String two = null;
        String three = "three";
        Assert.assertEquals("one three", RendererTools.spaceSeparateStrings(one, two, three));
    }

    @Test
    public void spaceSeparatedString4() {
        String one = "one";
        String two = null;
        String three = null;
        Assert.assertEquals("one", RendererTools.spaceSeparateStrings(one, two, three));
    }

    @Test
    public void spaceSeparatedString5() {
        String one_one = "one";
        String one_two = "one";
        String one_three = "one";
        Assert.assertEquals("one", RendererTools.spaceSeparateStrings(one_one, one_two, one_three));
    }

    @Test
    public void attributeValueAsBoolean() {
        Object stringTrue = "true";
        Object stringFalse = "false";
        Object booleanTrue = true;
        Object booleanFalse = false;
        Object booleanTruePrimitive = true;
        Object booleanFalsePrimitive = false;

        Assert.assertTrue(RendererTools.attributeValueAsBoolean(stringTrue, false));
        Assert.assertTrue(RendererTools.attributeValueAsBoolean(booleanTrue, false));
        Assert.assertTrue(RendererTools.attributeValueAsBoolean(booleanTruePrimitive, false));

        Assert.assertFalse(RendererTools.attributeValueAsBoolean(stringFalse, true));
        Assert.assertFalse(RendererTools.attributeValueAsBoolean(booleanFalse, true));
        Assert.assertFalse(RendererTools.attributeValueAsBoolean(booleanFalsePrimitive, true));
    }
}
