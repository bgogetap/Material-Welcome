package com.brandongogetap.library.base;

import android.content.Context;
import android.graphics.Color;

import com.brandongogetap.library.BuildConfig;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public final class WelcomeScreenCoordinatorTest {

    private static final int PAGE_ONE_COLOR = Color.WHITE;
    private static final int PAGE_TWO_COLOR = Color.BLUE;

    private WelcomeScreenContainer welcomeScreenContainer;
    private WelcomeScreenCoordinator coordinator;

    @Before
    public void setUp() {
        welcomeScreenContainer = Mockito.mock(WelcomeScreenContainer.class);
        List<WelcomeScreen> welcomeScreens = getDefaultWelcomeScreenList();
        coordinator = new TestWelcomeScreenCoordinator(
                welcomeScreens, welcomeScreenContainer, Mockito.mock(Context.class), 0);
    }

    @Test
    public void testAllListenersNotifiedAndBackgroundColorUpdatedOnScroll() {
        coordinator.onPageScrolled(0, 0.5f, 0);
        int resolvedColor = ColorBlender.blendColors(PAGE_ONE_COLOR, PAGE_TWO_COLOR, 0.5f);
        verify(welcomeScreenContainer, times(1)).updateBackgroundColor(resolvedColor);
        verify(coordinator.getScreenActionListener()).onPageScrolled(0, 0.5f, 0);
    }

    @Test
    public void testViewStateIsUpdatedWhenPageSelected() {
        int selectedPosition = 1;
        coordinator.onPageSelected(selectedPosition);
        verify(welcomeScreenContainer, times(1)).moveSelectedIndicator(selectedPosition);
        verify(welcomeScreenContainer, times(1)).updateWelcomeState(WelcomeState.MIDDLE);
        verify(coordinator.getScreenActionListener(), times(1)).onPageSelected(selectedPosition);
    }

    @Test
    public void testPageScrollStateIsForwardedToListener() {
        coordinator.onPageScrollStateChanged(SCROLL_STATE_DRAGGING);
        verify(coordinator.getScreenActionListener(), times(1))
                .onPageScrollStateChanged(SCROLL_STATE_DRAGGING);
    }

    @SuppressWarnings("ResourceType")
    public static List<WelcomeScreen> getDefaultWelcomeScreenList() {
        List<WelcomeScreen> screens = new ArrayList<>();
        screens.add(new WelcomeScreen.Builder(1).backgroundColor(PAGE_ONE_COLOR).build());
        screens.add(new WelcomeScreen.Builder(2).backgroundColor(PAGE_TWO_COLOR).build());
        screens.add(new WelcomeScreen.Builder(3).build());
        return screens;
    }
}
