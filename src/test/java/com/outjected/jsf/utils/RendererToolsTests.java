package com.outjected.jsf.utils;

import org.junit.Assert;
import org.junit.Test;

import com.outjected.jsf.foo.utils.RendererTools;

public class RendererToolsTests {

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
}
