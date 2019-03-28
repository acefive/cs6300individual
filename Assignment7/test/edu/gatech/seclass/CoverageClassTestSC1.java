package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CoverageClassTestSC1 {

    public CoverageClass myClass;


    @Before
    public void setUp() {
        myClass = new CoverageClass();
    }

    @Test
    public void testCoverageClassTestSC1() {
        myClass.coverageMethod1();
    }

    @After
    public void tearDown() {
        myClass = null;
    }
}
