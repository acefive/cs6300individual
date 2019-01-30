package edu.gatech.seclass.sdpencryptor;

import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasErrorText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;



@RunWith(AndroidJUnit4.class)
public class AssignmentExamples {

    @Rule
    public ActivityTestRule<SDPEncryptorActivity> tActivityRule = new ActivityTestRule<>(
            SDPEncryptorActivity.class);


    @Test
    public void Screenshot1() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Up with the White and Gold!"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("25"));
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("To vhsg sgd Vghsd zmc Fnkc!")));
    }

    @Test
    public void Screenshot2() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("123AbcCat123"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("3"));
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
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.shiftNumber)).check(matches(hasErrorText("Must Be Between 0 And 25")));
    }

    @Test
    public void ScreenshotErrors3() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Error Tests!"));
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.shiftNumber)).check(matches(hasErrorText("No Encryption Applied")));
    }

    @Test
    public void ScreenshotErrors4() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Error Tests!"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("0"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("0"));
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.rotateNumber)).check(matches(hasErrorText("No Encryption Applied")));
    }

    @Test
    public void ScreenshotLabel() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Cat"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("1"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("1"));
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("uDb")));
    }

    @Test
    public void ExtraTest1() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Lorem ipsum dolor sit amet"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("5"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("7"));
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("ny frjyQtwjr nuxzr itqtw x")));
    }

    @Test
    public void ExtraTest2() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Cat 123 Dog"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("0"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("50"));
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("23 DogCat 1")));
    }

    @Test
    public void ExtraTest3() {
        onView(withId(R.id.messageText)).perform(clearText(), typeText("Cat"));
        onView(withId(R.id.shiftNumber)).perform(clearText(), typeText("0"));
        onView(withId(R.id.rotateNumber)).perform(clearText(), typeText("6"));
        onView(withId(R.id.encryptButton)).perform(click());
        onView(withId(R.id.resultText)).check(matches(withText("Cat")));
    }

}