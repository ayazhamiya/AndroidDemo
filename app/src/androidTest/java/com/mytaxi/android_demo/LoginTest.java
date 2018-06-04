package com.mytaxi.android_demo;

import android.content.Context;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.mytaxi.android_demo.activities.AuthenticationActivity;
import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.utils.GetPropertyValue;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.io.IOException;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * Created by Ayaz on 6/2/2018.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {
    private static String uname;
    private static String password;
    private static String search_string;
    private static String req_driver ;

    @Rule
    public ActivityTestRule<AuthenticationActivity> mActivityRule = new ActivityTestRule<>(
            AuthenticationActivity.class, true, true);
    private AuthenticationActivity mActivity;
    private IdlingResource authIdlingResource;
    private MainActivity mainActivity;
    private IdlingResource driverSearchIdlingResource;


    @Before
    public void Setup(){
        //get idling resource for authentication
        authIdlingResource = mActivityRule.getActivity().getIdlingResourceForAuthActivity();

       //get current activity
        mActivity = mActivityRule.getActivity();
        Context context = mActivity.getApplicationContext();
        //load properties file
        GetPropertyValue properties = new GetPropertyValue();
        try {
            this.uname = properties.getPropertyValue("uname", context);
            this.password = properties.getPropertyValue("password", context);
            this.search_string = properties.getPropertyValue("search_string", context);
            this.req_driver = properties.getPropertyValue("req_driver", context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    //Test case to check if application is successfully logged in and user is able to search driver
    //and make a call.
    public void MyTaxi_Demo_Test(){
        Log.e("@Test_logs","Performing login to MyTaxi test");
        //find username field and enter username
        onView(withId(R.id.edt_username))
                .perform(ViewActions.typeText(uname),closeSoftKeyboard());
        Log.e("@Test_logs","username is entered");

        //find password field and enter password
        onView(withId(R.id.edt_password))
                .perform(ViewActions.typeText(password),closeSoftKeyboard());
        Log.e("@Test_logs","Password is entered");

        //click on enter button
        onView(withId(R.id.btn_login))
                .perform(click());
        Log.e("@Test_logs","Login button is clicked");

        //Register Idling resource for authentication call
        Espresso.registerIdlingResources(authIdlingResource);
        //check if search page is visible or not
        onView(withId(R.id.textSearch))
                .check(matches(isDisplayed()));
        Log.e("@Test_logs","login assertion successful");

        //type in search box to get drivers
        onView(withId(R.id.textSearch))
             .perform(ViewActions.typeText(search_string));

        // creating instance for main activity
        mainActivity = MainActivity.getInstance();
        //idling resource for driver search
        driverSearchIdlingResource = mainActivity.getIdlingResourceForMainActivity();

        Log.e("@Test_logs","Search string entered");
        //register driver search idling resource
        Espresso.registerIdlingResources(driverSearchIdlingResource);
        //select the desired driver from Recycler View
        onView(withText(req_driver))
             .inRoot(withDecorView(not(is(mActivity.getWindow().getDecorView()))))
             .perform(click());
        Log.e("@Test_logs","Desired driver is selected");

        //Click on Call button
         onView(withId(R.id.fab))
                 .perform(click());
        Log.e("@Test_logs","call button clicked");


 }
    @After
    public void TearDown(){
        //unregister idling resources
        //Espresso.unregisterIdlingResources(authIdlingResource);
       // Espresso.unregisterIdlingResources(driverSearchIdlingResource);
    }


}
