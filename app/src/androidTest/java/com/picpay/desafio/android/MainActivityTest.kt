package com.picpay.desafio.android

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.ui.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    private lateinit var robot: MainActivityTestRobot

    //TODO: Mock api requests with mockwebserver
    //TODO: Clear room database on @Before

    @Before
    fun setup() {
        robot = MainActivityTestRobot()
    }

    @Test
    fun shouldLoadAllItemsIntoRecyclerView() {
        robot.checkRecyclerViewItemCount(R.id.recyclerView, 33)
    }

    @Test
    fun shouldDisplayAllValuesIntoRecyclerViewItem() {
        robot
            .checkTextIsDisplayedOnRecyclerView(R.id.recyclerView, 0, "@eduardo.santos")
            .checkTextIsDisplayedOnRecyclerView(R.id.recyclerView, 0, "Eduardo Santos")
            .checkTextIsDisplayedOnRecyclerView(R.id.recyclerView, 1, "@marina.coelho")
            .checkTextIsDisplayedOnRecyclerView(R.id.recyclerView, 1, "Marina Coelho")
    }
}