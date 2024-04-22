package com.github.mrzahmadi.lightnote

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.github.mrzahmadi.lightnote.view.Screen
import com.github.mrzahmadi.lightnote.view.activity.AppNavHost
import com.github.mrzahmadi.lightnote.view.activity.MainActivity.Companion.NAVIGATION_BAR_ITEM_TAG_PREFIX
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runners.MethodSorters


@FixMethodOrder(MethodSorters.DEFAULT)
class NavigationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController


    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost(navController = navController)
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
        delay()
    }

    @Test
    fun navHost_verifyStartDestinationTitle() {
        val homeText = composeTestRule.activity.getString(R.string.home)
        composeTestRule
            .onAllNodesWithText(homeText)[1]
            .assertIsDisplayed()
        delay()
    }

    @Test
    fun navHost_verifyTabChange() {
        val favoriteText = composeTestRule.activity.getString(R.string.favorite)
        composeTestRule
            .onNodeWithTag(NAVIGATION_BAR_ITEM_TAG_PREFIX+1)
            .performClick()
        composeTestRule
            .onAllNodesWithText(favoriteText)[1]
            .assertIsDisplayed()
        Thread.sleep(300)

        val profileText = composeTestRule.activity.getString(R.string.profile)
        composeTestRule
            .onNodeWithTag(NAVIGATION_BAR_ITEM_TAG_PREFIX+2)
            .performClick()
        composeTestRule
            .onAllNodesWithText(profileText)[1]
            .assertIsDisplayed()
        Thread.sleep(300)

        val homeText = composeTestRule.activity.getString(R.string.home)
        composeTestRule
            .onNodeWithTag(NAVIGATION_BAR_ITEM_TAG_PREFIX+0)
            .performClick()
        composeTestRule
            .onAllNodesWithText(homeText)[1]
            .assertIsDisplayed()
    }

    private fun delay(){
        Thread.sleep(1000)
    }



}