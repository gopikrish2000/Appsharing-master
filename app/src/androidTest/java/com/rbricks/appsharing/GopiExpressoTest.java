package com.rbricks.appsharing;

import android.app.Activity;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingPolicies;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import com.rbricks.appsharing.concept.animations.PropertyAnimationActivity;
import com.rbricks.appsharing.utils.ElapsedTimeIdlingResource;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.runner.lifecycle.Stage.RESUMED;

/**
 * Created by gopikrishna on 22/11/16.
 */

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GopiExpressoTest {

    @Rule
    public ActivityTestRule<PropertyAnimationActivity> mActivityRule = new ActivityTestRule<>(PropertyAnimationActivity.class);


    //Sample Testing Usecases.
    @Test
    public void basicSearchFlowTest() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        onView(withId(R.id.action_search)).perform(click());
        onView(withId(R.id.notes_search_bar)).check(matches(isDisplayed()));
        onView(withId(R.id.notes_search_bar)).perform(typeText("gopi entering"), closeSoftKeyboard());
        onView(withId(R.id.notes_search_bar)).check(matches(withText("gopi entering")));
//        onView(withId(R.id.notes_search_bar)).perform(pressBack());
        onView(withText("Notes")).check(matches(isDisplayed()));
    }

    @Test
    public void basicItemEditTest() throws Exception {
        onView(withId(R.id.notes_listing_rv)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.notes_edit_btn)).check(matches(isDisplayed()));
//        Assert.assertTrue(getActivityInstance() instanceof NotesDetailsActivity);

        Espresso.pressBack();
        long waitingTime = 1000;
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(
                waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(
                waitingTime * 2, TimeUnit.MILLISECONDS);

        // Now we wait
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);

        Espresso.unregisterIdlingResources(idlingResource);
    }

    @Test
    public void basicFlow() throws Exception {
        onView(withId(R.id.processanimation_et)).perform(typeText("1"), closeSoftKeyboard());
        waitForTime(20000); // not working
    }

    public void waitForTime(long waitingTime) {
        // Make sure Espresso does not time out
        IdlingPolicies.setMasterPolicyTimeout(
                waitingTime * 2, TimeUnit.MILLISECONDS);
        IdlingPolicies.setIdlingResourceTimeout(
                waitingTime * 2, TimeUnit.MILLISECONDS);

        // Now we wait
        IdlingResource idlingResource = new ElapsedTimeIdlingResource(waitingTime);
        Espresso.registerIdlingResources(idlingResource);

        Espresso.unregisterIdlingResources(idlingResource);
    }

    public Activity getActivityInstance() {
        final Activity[] activity = new Activity[1];
        InstrumentationRegistry.getInstrumentation().runOnMainSync(() -> {
            Activity currentActivity = null;
            Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(RESUMED);
            if (resumedActivities.iterator().hasNext()) {
                currentActivity = (Activity) resumedActivities.iterator().next();
                activity[0] = currentActivity;
            }
        });
        return activity[0];
    }
}