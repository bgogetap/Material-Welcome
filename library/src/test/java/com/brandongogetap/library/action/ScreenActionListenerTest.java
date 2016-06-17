package com.brandongogetap.library.action;

import android.graphics.Color;

import com.brandongogetap.library.BuildConfig;
import com.brandongogetap.library.base.WelcomeScreen;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public final class ScreenActionListenerTest {

    private static final int PAGE_ONE_COLOR = Color.WHITE;
    private static final int PAGE_TWO_COLOR = Color.BLUE;

    private ScreenActionListener screenActionListener;
    private ScreenAction screenAction;

    @Before public void setUp() {
        screenAction = Mockito.mock(ScreenAction.class);
        screenActionListener = new ScreenActionListener(
                getWelcomeScreenListWithScreenAction(screenAction));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testScreenActionIsCalledWithCorrectValues() {
        // Called twice because direction is determined using delta
        screenActionListener.onPageScrolled(0, 0.5f, 0);
        screenActionListener.onPageScrolled(0, 0.4f, 0);
        verify(screenAction).screenScrollingOut(null, 0.4f, ScreenActionListener.BACKWARD);
        screenActionListener.onPageScrolled(0, 0.6f, 0);
        verify(screenAction).screenScrollingIn(null, 0.6f, ScreenActionListener.FORWARD);
    }

    @Test
    public void testScreenActionIsCalledWhenPageSettled() {
        screenActionListener.onPageSelected(1);
        //noinspection ConstantConditions
        verify(screenAction, times(1)).screenSettled(null);
    }

    @SuppressWarnings("ResourceType")
    private static List<WelcomeScreen> getWelcomeScreenListWithScreenAction(ScreenAction screenAction) {
        List<WelcomeScreen> screens = new ArrayList<>();
        screens.add(new WelcomeScreen.Builder(1).backgroundColor(PAGE_ONE_COLOR).build());
        screens.add(new WelcomeScreen.Builder(2)
                .backgroundColor(PAGE_TWO_COLOR)
                .screenAction(screenAction)
                .build());
        screens.add(new WelcomeScreen.Builder(3).build());
        return screens;
    }
}
