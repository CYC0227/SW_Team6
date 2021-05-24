package com.example.see;

import android.os.IBinder;
import android.view.WindowManager;

import androidx.test.espresso.Root;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

public class MainActivityTest {

    /**
     * Class to check toast message is shown.
     */
    public class ToastMatcher extends TypeSafeMatcher<Root> {

        private String message;

        public ToastMatcher(String message) {
            this.message = message;
        }

        @Override
        protected boolean matchesSafely(Root root) {
            int type = root.getWindowLayoutParams().get().type;
            if (type == WindowManager.LayoutParams.TYPE_TOAST) {
                IBinder windowToken = root.getDecorView().getWindowToken();
                IBinder appToken = root.getDecorView().getApplicationWindowToken();
                if (windowToken == appToken) {
                    // means this window isn't contained by any other windows.
                    return true;
                }
            }
            return false;
        }

        @Override
        public void describeTo(Description description) {
            description.appendText(message);
        }

    }

    @Test
    public void isToastMessageDisplayed() {
        onView(withText("로그인 성공"))
                .inRoot(new ToastMatcher("로그인 성공"))
                .check(matches(isDisplayed()));
    }
}