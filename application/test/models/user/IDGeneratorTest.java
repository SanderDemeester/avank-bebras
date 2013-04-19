package models.user;

import org.junit.Test;
import org.junit.Assert;

import models.user.IDGenerator;

/**
 * Test class for the Identifier generator.
 * @author Felix Van der Jeugt
 */
public class IDGeneratorTest {

    @Test public void testCleanName() {
        Assert.assertEquals("frederique", IDGenerator.cleanName("Frédérique"));
        Assert.assertEquals("pieter jan", IDGenerator.cleanName("Pieter-Jan"));
        Assert.assertEquals("ea",         IDGenerator.cleanName("ëáα"));
        Assert.assertEquals("",           IDGenerator.cleanName("2#$!)))"));
    }

}
