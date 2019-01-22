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

    //Test Purpose: This is to test corner case of \n.
    @Test
    public void testCountNumbers2() {
        mycustomstring.setString("333 55555 \n22 333");
        assertEquals(4, mycustomstring.countNumbers());
    }

    //Test Purpose: This is to test corner case of leading and ending spaces.
    @Test
    public void testCountNumbers3() {
        mycustomstring.setString("  \t   333 55555 \n22 333    ");
        assertEquals(4, mycustomstring.countNumbers());
    }

    //Test Purpose: This is to test corner case of \0.
    @Test
    public void testCountNumbers4() {
        mycustomstring.setString("333 55555 \0 22 333");
        assertEquals(4, mycustomstring.countNumbers());
    }

    //Test Purpose: This is to test corner case of hexadecimal.
    @Test(expected = AssertionError.class)
    public void testCountNumbers5() {
        mycustomstring.setString("333 55555 0x400 22 333");
        assertEquals(5, mycustomstring.countNumbers());
    }

    //Test Purpose: This is to test corner case of only one element.
    @Test
    public void testCountNumbers6() {
        mycustomstring.setString("1");
        assertEquals(1, mycustomstring.countNumbers());
    }

    //Test Purpose: This is to test corner case of empty string.
    @Test
    public void testCountNumbers7() {
        mycustomstring.setString("");
        assertEquals(0, mycustomstring.countNumbers());
    }

    //Test Purpose: This is to test corner case of non-initialized string.
    @Test
    public void testCountNumbers8() {
        assertEquals(0, mycustomstring.countNumbers());
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

    //Test Purpose: This is to test corner case of negative n.
    @Test(expected = IllegalArgumentException.class)
    public void testAddDigits3() {
        mycustomstring.setString("1234567890");
        assertEquals("9012345678", mycustomstring.addDigits(-32, false));
    }

    //Test Purpose: This is to test corner case of big n.
    @Test(expected = IllegalArgumentException.class)
    public void testAddDigits4() {
        mycustomstring.setString("1234567890");
        assertEquals("9012345678", mycustomstring.addDigits(28, false));
    }

    //Test Purpose: This is to test corner case of null pointer.
    @Test(expected = NullPointerException.class)
    public void testAddDigits5() {
        assertEquals("", mycustomstring.addDigits(3, false));
    }

    //Test Purpose: This is to test corner case of empty string.
    @Test
    public void testAddDigits6() {
        mycustomstring.setString("");
        assertEquals("", mycustomstring.addDigits(3, false));
    }

    //Test Purpose: This is to test corner case of null pointer and reverse.
    @Test(expected = NullPointerException.class)
    public void testAddDigits7() {
        assertEquals("", mycustomstring.addDigits(3, true));
    }

    //Test Purpose: This is to test corner case of empty string and reverse.
    @Test
    public void testAddDigits8() {
        mycustomstring.setString("");
        assertEquals("", mycustomstring.addDigits(8, true));
    }

    //Test Purpose: This is to test corner case of big n and reverse.
    @Test(expected = IllegalArgumentException.class)
    public void testAddDigits9() {
        mycustomstring.setString("1234567890");
        assertEquals("", mycustomstring.addDigits(28, true));
    }

    //Test Purpose: This is to test corner case of negative n and reverse.
    @Test(expected = IllegalArgumentException.class)
    public void testAddDigits10() {
        mycustomstring.setString("1234567890");
        assertEquals("", mycustomstring.addDigits(-28, true));
    }

    //Test Purpose: This is to test corner case of long string and reverse.
    @Test
    public void testAddDigits11() {
        mycustomstring.setString("1234567890123456789009876543211234567890");
        assertEquals("8765432109901234567887654321098765432109", mycustomstring.addDigits(8, true));
    }

    //Test Purpose: This is to test corner case of long string.
    @Test
    public void testAddDigits12() {
        mycustomstring.setString("1234567890123456789009876543211234567890");
        assertEquals("9012345678901234567887654321099012345678", mycustomstring.addDigits(8, false));
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

    //Test Purpose: This is to test corner case of negative startPosition.
    @Test(expected = IllegalArgumentException.class)
    public void testConvertDigitsToNamesInSubstring3() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(-3, 23);
        assertEquals("I'd b3tt3r put s*zero*me d*onesix*1ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    //Test Purpose: This is to test corner case of big endPosition.
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring4() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(5, 78);
        assertEquals("I'd b3tt3r put s*zero*me d*onesix*1ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    //Test Purpose: This is to test corner case of when startPosition > endPosition.
    @Test(expected = IllegalArgumentException.class)
    public void testConvertDigitsToNamesInSubstring5() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(24, 23);
        assertEquals("I'd b3tt3r put s*zero*me d*onesix*1ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    //Test Purpose: This is to test corner case of special place.
    @Test
    public void testConvertDigitsToNamesInSubstring6() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(3, 7);
        assertEquals("I'd b*three*tt3r put s0me d161ts in this 5tr1n6, right?", mycustomstring.getString());
    }

    //Test Purpose: This is to test corner case of ending substring.
    @Test
    public void testConvertDigitsToNamesInSubstring7() {
        mycustomstring.setString("333 55555 *22*");
        mycustomstring.convertDigitsToNamesInSubstring(13, 14);
        assertEquals("333 55555 *2*two**", mycustomstring.getString());
    }

    //Test Purpose: This is to test corner case of ending substring.
    @Test
    public void testConvertDigitsToNamesInSubstring8() {
        mycustomstring.setString("333 55555 *22*");
        mycustomstring.convertDigitsToNamesInSubstring(12, 14);
        assertEquals("333 55555 **twotwo**", mycustomstring.getString());
    }

    //Test Purpose: This is to test corner case of leading substring.
    @Test
    public void testConvertDigitsToNamesInSubstring9() {
        mycustomstring.setString("333 55555 *22*");
        mycustomstring.convertDigitsToNamesInSubstring(1, 6);
        assertEquals("*threethreethree* *fivefive*555 *22*", mycustomstring.getString());
    }

    //Test Purpose: This is to test corner case of whole string.
    @Test
    public void testConvertDigitsToNamesInSubstring10() {
        mycustomstring.setString("333 55555 *22*");
        mycustomstring.convertDigitsToNamesInSubstring(1, 14);
        assertEquals("*threethreethree* *fivefivefivefivefive* **twotwo**", mycustomstring.getString());
    }

}
