package com.outjected.jsf.utils;

import org.junit.Assert;
import org.junit.Test;

public class RendererToolsTest {

    @Test
    public void spaceSeperatedString1() {
        String one = "one";
        String two = "two";
        String three = "three";
        Assert.assertEquals("one two three", RendererTools.spaceSeperateStrings(one, two, three));
    }

    @Test
    public void spaceSeperatedString2() {
        String one = "one";
        String two = "two bar";
        String three = "three";
        Assert.assertEquals("one two bar three", RendererTools.spaceSeperateStrings(one, two, three));
    }

    @Test
    public void spaceSeperatedString3() {
        String one = "one";
        String two = null;
        String three = "three";
        Assert.assertEquals("one three", RendererTools.spaceSeperateStrings(one, two, three));
    }

    @Test
    public void spaceSeperatedString4() {
        String one = "one";
        String two = null;
        String three = null;
        Assert.assertEquals("one", RendererTools.spaceSeperateStrings(one, two, three));
    }

    @Test
    public void spaceSeperatedString5() {
        String one_one = "one";
        String one_two = "one";
        String one_three = "one";
        Assert.assertEquals("one", RendererTools.spaceSeperateStrings(one_one, one_two, one_three));
    }

    @Test
    public void attributeValueAsBoolean() {
        Object stringTrue = "true";
        Object stringFalse = "false";
        Object booleanTrue = Boolean.TRUE;
        Object booleanFalse = Boolean.FALSE;
        Object booleanTruePrimitive = true;
        Object booleanFalsePrimitive = false;

        Assert.assertTrue(RendererTools.attributeValueAsBoolean(stringTrue, false));
        Assert.assertTrue(RendererTools.attributeValueAsBoolean(booleanTrue, false));
        Assert.assertTrue(RendererTools.attributeValueAsBoolean(booleanTruePrimitive, false));
        Assert.assertTrue(RendererTools.attributeValueAsBoolean(null, true));

        Assert.assertFalse(RendererTools.attributeValueAsBoolean(stringFalse, true));
        Assert.assertFalse(RendererTools.attributeValueAsBoolean(booleanFalse, true));
        Assert.assertFalse(RendererTools.attributeValueAsBoolean(booleanFalsePrimitive, true));
        Assert.assertFalse(RendererTools.attributeValueAsBoolean(null, false));
    }
}
