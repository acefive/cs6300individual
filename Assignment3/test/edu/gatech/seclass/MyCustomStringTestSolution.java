package edu.gatech.seclass;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Junit test class created for use in Georgia Tech CS6300 Spring 2019.
 *
 * This is an test class for a simple class that represents a string, defined
 * as a sequence of characters.
 *
 * This class is provided to interpret your grades via junit tests
 * and as a reminder, should NOT be posted in any public repositories,
 * even after the class has ended.
 */

public class MyCustomStringTestSolution {

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

    //special characters, first character, last character
    @Test
    public void testCountNumbers2() {
        mycustomstring.setString("l3t'5 put s0me d161ts in this 5tr1n6!11!! *()'& some math 123-08.5");
        assertEquals(11, mycustomstring.countNumbers());
    }

    //no digits
    @Test
    public void testCountNumbers3() {
        mycustomstring.setString("And what if I have no digits?");
        assertEquals(0, mycustomstring.countNumbers());
    }

    //all digits
    @Test
    public void testCountNumbers4() {
        mycustomstring.setString("1 2 3 4 5 01234567890 6 7 8 9 0");
        assertEquals(11, mycustomstring.countNumbers());
    }

    //empty string
    @Test
    public void testCountNumbers5() {
        mycustomstring.setString("");
        assertEquals(0, mycustomstring.countNumbers());
    }


    //unset (unitialized string)
    @Test
    public void testCountNumbers6() {

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

    //0 length string
    @Test
    public void testAddDigits3() {

        mycustomstring.setString("");
        assertEquals("", mycustomstring.addDigits(1, true));
    }

    //single character
    @Test
    public void testAddDigits4() {

        mycustomstring.setString("9");
        assertEquals("1", mycustomstring.addDigits(2, true));
    }

    //only numbers
    @Test
    public void testAddDigits5() {

        mycustomstring.setString("01234567890");
        assertEquals("56789012345", mycustomstring.addDigits(5, false));
    }

    //no numbers
    @Test
    public void testAddDigits6() {

        mycustomstring.setString("Hey this has NO numbers!");
        assertEquals("Hey this has NO numbers!", mycustomstring.addDigits(1, true));
    }

    //1 boundary increase value - lower bound of valid argument
    @Test
    public void testAddDigits7() {

        mycustomstring.setString("H3y, 13t'5 put 50me d161ts in this 5tr1n6!11!!");
        assertEquals("H4y, 42t'6 put 16me d272ts in this 6tr2n7!22!!", mycustomstring.addDigits(1, true));
    }

    //9 boundary for digits - upper bound of valid arguments
    @Test
    public void testAddDigits8() {

        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        assertEquals("H2y, l2t'4 put 49me d050ts in this 4tr0n5!00!!", mycustomstring.addDigits(9, false));
    }

    //zero illegal argument
    @Test(expected = IllegalArgumentException.class)
    public void testAddDigits9() {

        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        mycustomstring.addDigits(0, false);
    }

    //null string
    @Test(expected = NullPointerException.class)
    public void testAddDigits10() {

        mycustomstring.addDigits(1, true);
    }

    //positive illegal argument
    @Test(expected = IllegalArgumentException.class)
    public void testIncreaseDigits11() {
        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        mycustomstring.addDigits(10, true);
    }


    //negative illegal argument
    @Test(expected = IllegalArgumentException.class)
    public void testAddDigits12() {

        mycustomstring.setString("H3y, l3t'5 put 50me d161ts in this 5tr1n6!11!!");
        mycustomstring.addDigits(-1, false);
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

    //convert entire string with numbers at start/end positions
    @Test
    public void testConvertDigitsToNamesInSubstring3() {
        mycustomstring.setString("1'd b3tt3r put s0me d161t5 1n this 5tr1n6, right?1!1");
        mycustomstring.convertDigitsToNamesInSubstring(1, 52);
        assertEquals("*one*'d b*three*tt*three*r put s*zero*me d*onesixone*t*five* *one*n this *five*tr*one*n*six*, right?*one*!*one*", mycustomstring.getString());
    }

    //test all numbers
    @Test
    public void testConvertDigitsToNamesInSubstring4() {
        mycustomstring.setString("All the numbers: 0123456789 converted");
        mycustomstring.convertDigitsToNamesInSubstring(15, 30);
        assertEquals("All the numbers: *zeroonetwothreefourfivesixseveneightnine* converted",
                mycustomstring.getString());
    }

    //special characters, begin and ending positions white space (do not trim), separate by spaces & period
    @Test
    public void testConvertDigitsToNamesInSubstring5() {
        mycustomstring.setString(" 987.654+321 012 %^&*75)(!@*#0 Well, that has enough digits. ");
        mycustomstring.convertDigitsToNamesInSubstring(1, 31);
        assertEquals(" *nineeightseven*.*sixfivefour*+*threetwoone* *zeroonetwo* %^&**sevenfive*)(!@*#*zero* Well, that has enough digits. ",
                mycustomstring.getString());
    }

    //convert a single digit, with end = start
    @Test
    public void testConvertDigitsToNamesInSubstring6() {
        mycustomstring.setString("1");
        mycustomstring.convertDigitsToNamesInSubstring(1, 1);
        assertEquals("*one*",
                mycustomstring.getString());
    }

    //start < 1 AND end > length, test order of exceptions
    @Test(expected = IllegalArgumentException.class)
    public void testConvertDigitsToNamesInSubstring7() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(-1, 200);
    }

    //start position < 1
    @Test(expected = IllegalArgumentException.class)
    public void testConvertDigitsToNamesInSubstring8() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(0, 10);
    }

    //start > end
    @Test(expected = IllegalArgumentException.class)
    public void testConvertDigitsToNamesInSubstring9() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(100, 20);
    }


    //end position > length
    @Test(expected = MyIndexOutOfBoundsException.class)
    public void testConvertDigitsToNamesInSubstring10() {
        mycustomstring.setString("I'd b3tt3r put s0me d161ts in this 5tr1n6, right?");
        mycustomstring.convertDigitsToNamesInSubstring(30, 80);
    }

}
