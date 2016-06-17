package com.brandongogetap.library.base;

import android.content.Context;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewPager;

import com.brandongogetap.library.action.ScreenActionListener;

import java.util.List;

import static com.brandongogetap.library.base.ColorBlender.blendColors;

class WelcomeScreenCoordinator implements ViewPager.OnPageChangeListener {

    private final List<WelcomeScreen> welcomeScreens;
    private final WelcomeScreenContainer container;
    private final ScreenActionListener screenActionListener;
    private final Context context;

    WelcomeScreenCoordinator(
            List<WelcomeScreen> welcomeScreens,
            WelcomeScreenContainer container,
            Context context,
            int startingPosition
    ) {
        this.welcomeScreens = welcomeScreens;
        this.container = container;
        this.screenActionListener = initScreenActionListener(welcomeScreens);
        this.context = context;
        container.updateBackgroundColor(
                welcomeScreens.get(startingPosition).getBackgroundColor(context));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            int firstColor = welcomeScreens.get(position).getBackgroundColor(context);
            int secondColor = welcomeScreens.get(position + 1).getBackgroundColor(context);
            container.updateBackgroundColor(blendColors(firstColor, secondColor, positionOffset));
        }
        screenActionListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
    }

    @Override public void onPageSelected(int position) {
        container.moveSelectedIndicator(position);
        screenActionListener.onPageSelected(position);
        updateWelcomeState(position);
    }

    @Override public void onPageScrollStateChanged(int state) {
        screenActionListener.onPageScrollStateChanged(state);
    }

    void updateWelcomeState(int currentItem) {
        WelcomeState state;
        if (currentItem == 0) {
            state = WelcomeState.FIRST_PAGE;
        } else if (currentItem == welcomeScreens.size() - 1) {
            state = WelcomeState.LAST_PAGE;
        } else {
            state = WelcomeState.MIDDLE;
        }
        container.updateWelcomeState(state);
    }

    // Allows for test subclasses to provide mocked implementation
    protected ScreenActionListener initScreenActionListener(List<WelcomeScreen> welcomeScreens) {
        return new ScreenActionListener(welcomeScreens);
    }

    @VisibleForTesting
    ScreenActionListener getScreenActionListener() {
        return screenActionListener;
    }
}
