package edu.gatech.seclass.sdpencryptor;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;



@RunWith(AndroidJUnit4.class)
public class EspressoGradingTests {

    @Rule
    public ActivityTestRule<SDPEncryptorActivity> tActivityRule = new ActivityTestRule<>(
            SDPEncryptorActivity.class);

    @Test
    public void InitializedShift() {
        onView(withId(R.id.shiftNumber)).check(matches(withText("0")));
    }

    @Test
    public void InitializedRotate() {
        onView(withId(R.id.rotateNumber)).check(matches(withText("0")));
    }

    @Test
    public void InitializedMessage() {
        onView(withId(R.id.messageText)).check(matches(withText("")));
    }

    @Test
    public void THWG() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("To Hell With Georgia!"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("10"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("!Dy Rovv Gsdr Qoybqsk")));
    }

    @Test
    public void POIB() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Prof Orso Is 1337"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("1"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("7Qspg Pstp Jt 133")));
    }

    @Test
    public void normaltest1() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Lions & Tigers & Bears"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("20"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("5"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("VyulmFcihm & Ncaylm & ")));
    }

    @Test
    public void normaltest2() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Panda Cat"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("15"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("31"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText(" RpiEpcsp")));
    }

    @Test
    public void normaltest3() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("CS6300 - SDP"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("0"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("17"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("- SDPCS6300 ")));
    }

    @Test
    public void normaltest4() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("   a "));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("25"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("    z")));
    }

    @Test
    public void normaltest5() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("The quick brown fox jumps over the lazy dog"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("10"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("15"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("ob dro vkji nyqDro aesmu lbygx pyh tewzc yf")));
    }

    @Test
    public void WeAreTheBest() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("We are the best"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("4"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("Ai evi xli fiwx")));
    }

    @Test
    public void NotSoFast() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("racecar"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("0"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("7"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("racecar")));
    }

    @Test
    public void Wreck() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Ramblin' Wreck"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("2"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("Tcodnkp' Ytgem")));
    }


    @Test
    public void ScreenshotErrors1a() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("!! * !!"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("1"));
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.messageText)).check(matches(hasErrorText("Alphabetic Message Required")));
    }

    @Test
    public void ScreenshotErrors4a() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Error Tests!"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("5"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("5"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("0"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.rotateNumber)).check(matches(hasErrorText("No Encryption Applied")));
    }


    //PROVIDED TESTS:

    @Test
    public void Screenshot1() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Up with the White and Gold!"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("25"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("To vhsg sgd Vghsd zmc Fnkc!")));
    }

    @Test
    public void Screenshot2() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("123AbcCat123"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("3"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("123123AbcCat")));
    }

    @Test
    public void ScreenshotErrors1() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("35505!"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("5"));
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.messageText)).check(matches(hasErrorText("Alphabetic Message Required")));
    }

    @Test
    public void ScreenshotErrors2() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Another Error Test"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("50"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.shiftNumber)).check(matches(hasErrorText("Must Be Between 0 And 25")));
    }

    @Test
    public void ScreenshotErrors3() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Error Tests!"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.shiftNumber)).check(matches(hasErrorText("No Encryption Applied")));
    }

    @Test
    public void ScreenshotErrors4() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Error Tests!"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("0"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.rotateNumber)).check(matches(hasErrorText("No Encryption Applied")));
    }

    @Test
    public void ScreenshotLabel() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Cat"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("1"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("1"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("uDb")));
    }

    @Test
    public void ExtraTest1() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Lorem ipsum dolor sit amet"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("5"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("7"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("ny frjyQtwjr nuxzr itqtw x")));
    }

    @Test
    public void ExtraTest2() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Cat 123 Dog"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("0"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("50"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("23 DogCat 1")));
    }

    @Test
    public void ExtraTest3() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Cat"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("0"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("6"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("Cat")));
    }

}