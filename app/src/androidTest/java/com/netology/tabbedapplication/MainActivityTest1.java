package com.netology.tabbedapplication;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest1 {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void mainActivityTest1() {
        ViewInteraction textView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withText("TAB 1"),
                        ViewMatchers.withParent(Matchers.allOf(ViewMatchers.withContentDescription("Tab 1"),
                                ViewMatchers.withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class)))),
                        ViewMatchers.isDisplayed()));
        textView.check(ViewAssertions.matches(ViewMatchers.withText("TAB 1")));

        ViewInteraction textView2 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.section_label), ViewMatchers.withText("Page: 1"),
                        ViewMatchers.withParent(Matchers.allOf(ViewMatchers.withId(R.id.constraintLayout),
                                ViewMatchers.withParent(ViewMatchers.withId(R.id.view_pager)))),
                        ViewMatchers.isDisplayed()));
        textView2.check(ViewAssertions.matches(ViewMatchers.withText("Page: 1")));

        ViewInteraction tabView = Espresso.onView(
                Matchers.allOf(ViewMatchers.withContentDescription("Tab 2"),
                        childAtPosition(
                                childAtPosition(
                                        ViewMatchers.withId(R.id.tabs),
                                        0),
                                1),
                        ViewMatchers.isDisplayed()));
        tabView.perform(ViewActions.click());

        ViewInteraction textView3 = Espresso.onView(
                Matchers.allOf(ViewMatchers.withId(R.id.section_label), ViewMatchers.withText("Page: 2"),
                        ViewMatchers.withParent(Matchers.allOf(ViewMatchers.withId(R.id.constraintLayout),
                                ViewMatchers.withParent(ViewMatchers.withId(R.id.view_pager)))),
                        ViewMatchers.isDisplayed()));
        textView3.check(ViewAssertions.matches(ViewMatchers.withText("Page: 2")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
