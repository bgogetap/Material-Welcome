package com.brandongogetap.library.base;

import android.content.Context;

import com.brandongogetap.library.action.ScreenActionListener;

import org.mockito.Mockito;

import java.util.List;

final class TestWelcomeScreenCoordinator extends WelcomeScreenCoordinator {

    TestWelcomeScreenCoordinator(
            List<WelcomeScreen> welcomeScreens,
            WelcomeScreenContainer container,
            Context context,
            int startingPosition
    ) {
        super(welcomeScreens, container, context, startingPosition);
    }

    @Override
    protected ScreenActionListener initScreenActionListener(List<WelcomeScreen> welcomeScreens) {
        return Mockito.mock(ScreenActionListener.class);
    }
}
