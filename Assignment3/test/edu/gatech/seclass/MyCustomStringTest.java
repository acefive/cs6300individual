package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Junit test class created for use in Georgia Tech CS6300.
 *
 * This is an test class for a simple class that represents a string, defined
 * as a sequence of characters.
 *
 * You should implement your tests in this class.  Do not change the test names.
 */

public class MyCustomStringTest {

    private MyCustomStringInterface mycustomstring;

    @Before
    public void setUp() {
        mycustomstring = new MyCustomString();
    }

    @After
    public void tearDown() {
        mycustomstring = null;
    }

    //Test Purpose: This is the first instructor example test.
    @Test
    public void testCountNumbers1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        assertEquals(7, mycustomstring.countNumbers());
    }

    @Test
    public void testCountNumbers2() {
        fail("Not yet implemented");
    }

    @Test
    public void testCountNumbers3() {
        fail("Not yet implemented");
    }

    @Test
    public void testCountNumbers4() {
        fail("Not yet implemented");
    }

    @Test
    public void testCountNumbers5() {
        fail("Not yet implemented");
    }

    @Test
    public void testCountNumbers6() {
        fail("Not yet implemented");
    }

    //Test Purpose: This is the second instructor example test.
    @Test
    public void testAddDigits1() {
        mycustomstring.setString("1234!!! H3y, L3t'5 put 50me d161ts in this 5tr1n6!11!1");
        assertEquals("5678!!! H7y, L7t'9 put 94me d505ts in this 9tr5n0!55!5", mycustomstring.addDigits(4, false));
    }

    //Test Purpose: This the third instructor example test.
    @Test
    public void testAddDigits2() {
        mycustomstring.setString("1234!!! H3y, L3t'5 put 50me d161ts in this 5tr1n6!11!1");
        assertEquals("8765!!! H7y, L7t'9 put 49me d505ts in this 9tr5n0!55!5", mycustomstring.addDigits(4, true));
    }

    @Test
    public void testAddDigits3() {
        
        fail("Not yet implemented");
    }

    @Test
    public void testAddDigits4() {

        fail("Not yet implemented");
    }

    @Test
    public void testAddDigits5() {

        fail("Not yet implemented");
    }

    @Test
    public void testAddDigits6() {

        fail("Not yet implemented");
    }

    @Test
    public void testAddDigits7() {
        
        fail("Not yet implemented");
    }

    @Test
    public void testAddDigits8() {
        
        fail("Not yet implemented");
    }

    @Test
    public void testAddDigits9() {
        
        fail("Not yet implemented");
    }

    @Test
    public void testAddDigits10() {
        
        fail("Not yet implemented");
    }

    @Test
    public void testAddDigits11() {
        
        fail("Not yet implemented");
    }

    @Test
    public void testAddDigits12() {
        
        fail("Not yet implemented");
    }


    //Test Purpose: This is the fourth instructor example test.
    @Test
    public void testConvertDigitsToNamesInSubstring1() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(17, 23);
        assertEquals("I'd b3tt3r put s*zero*me d*onesix*1ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    //Test Purpose: This is the fifth instructor example test, demonstrating a test for an exception.  Your exceptions should be tested in this format.
    @Test(expected = NullPointerException.class)
    public void testConvertDigitsToNamesInSubstring2() {
        mycustomstring.convertDigitsToNamesInSubstring(2, 10);
    }

    @Test
    public void testConvertDigitsToNamesInSubstring3() {
        fail("Not yet implemented");
    }

    @Test
    public void testConvertDigitsToNamesInSubstring4() {
        fail("Not yet implemented");
    }

    @Test
    public void testConvertDigitsToNamesInSubstring5() {
        fail("Not yet implemented");
    }

    @Test
    public void testConvertDigitsToNamesInSubstring6() {
        fail("Not yet implemented");
    }

    @Test
    public void testConvertDigitsToNamesInSubstring7() {
        fail("Not yet implemented");
    }

    @Test
    public void testConvertDigitsToNamesInSubstring8() {
        fail("Not yet implemented");
    }

    @Test
    public void testConvertDigitsToNamesInSubstring9() {
        fail("Not yet implemented");
    }

    @Test
    public void testConvertDigitsToNamesInSubstring10() {
        fail("Not yet implemented");
    }

}
