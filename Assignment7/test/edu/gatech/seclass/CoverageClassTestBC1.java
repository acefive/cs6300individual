package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CoverageClassTestBC1 {

    public CoverageClass myClass;


    @Before
    public void setUp() {
        myClass = new CoverageClass();
    }

    @Test
    public void testCoverageClassTestBC1() {
        myClass.coverageMethod1(1);
        myClass.coverageMethod1(0);
    }

    @After
    public void tearDown() {
        myClass = null;
    }

}
