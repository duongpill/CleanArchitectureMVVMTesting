package com.duongnh.catastrophic.presentation.cat

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.duongnh.catastrophic.di.DatabaseModule
import com.duongnh.catastrophic.di.NetworkModule
import com.duongnh.catastrophic.navigation.MyNavDestination
import com.duongnh.catastrophic.presentation.MainActivity
import com.duongnh.catastrophic.ui.theme.CATastrophicTheme
import com.duongnh.catastrophic.utils.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(NetworkModule::class, DatabaseModule::class)
class CatGalleryScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.apply {
            setContent {
                val navController = rememberNavController()
                CATastrophicTheme {
                    NavHost(
                        navController = navController,
                        startDestination = MyNavDestination.CAT_LIST_ROUTE
                    ) {
                        composable(route = MyNavDestination.CAT_LIST_ROUTE) {
                            CatGalleryRoute()
                        }
                    }
                }
            }
        }
    }

    @Test
    fun clickPhoto_isVisible() {
        composeRule.onNodeWithTag(TestTags.PHOTO_SECTION).assertIsNotDisplayed()
        composeRule.waitUntil {
            composeRule.onAllNodesWithTag(TestTags.PHOTO_ITEM_SECTION).apply {
                fetchSemanticsNodes().forEachIndexed { index, _ ->
                    get(index).performClick()
                    composeRule.onNodeWithTag(TestTags.PHOTO_SECTION).assertIsDisplayed()
                }
            }
            true
        }
    }
}