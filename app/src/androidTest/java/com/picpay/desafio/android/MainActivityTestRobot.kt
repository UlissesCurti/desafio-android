package com.picpay.desafio.android

import android.content.Context
import android.net.wifi.WifiManager
import androidx.annotation.IdRes
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasChildCount
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.picpay.desafio.android.core.PicPayApplication

class MainActivityTestRobot {

    private fun onView(@IdRes resId: Int): ViewInteraction {
        return onView(withId(resId))
    }

    fun checkRecyclerViewItemCount(@IdRes recyclerViewId: Int, itemCount: Int) = apply {
        onView(recyclerViewId).check(matches(hasChildCount(itemCount)))
    }

    fun checkTextIsDisplayedOnRecyclerView(
        @IdRes recyclerViewId: Int,
        position: Int,
        text: String
    ) = apply {
        onView(recyclerViewId)
            .check(
                matches(
                    RecyclerViewMatchers.atPosition(
                        position,
                        ViewMatchers.hasDescendant(
                            ViewMatchers.withText(text)
                        )
                    )
                )
            )
    }
}