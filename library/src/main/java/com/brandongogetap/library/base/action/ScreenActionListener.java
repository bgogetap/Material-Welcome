package com.brandongogetap.library.base.action;

import android.support.annotation.IntDef;

import com.brandongogetap.library.base.WelcomeScreen;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class ScreenActionListener {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({FORWARD, BACKWARD})
    public @interface ScrollDirection {
    }

    static final int FORWARD = 0;
    static final int BACKWARD = 1;

    private final List<WelcomeScreen> screenList;

    private float lastPositionOffset = -1;

    public ScreenActionListener(List<WelcomeScreen> screenList) {
        this.screenList = screenList;
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (lastPositionOffset != -1 && positionOffset > 0) {
            int scrollDirection = positionOffset - lastPositionOffset > 0 ? FORWARD : BACKWARD;
            WelcomeScreen currentScreen = screenList.get(position);
            ScreenAction currentScreenAction = currentScreen.getScreenAction();

            WelcomeScreen otherScreen = screenList.get(position + 1);
            ScreenAction otherScreenAction = otherScreen.getScreenAction();

            if (currentScreenAction != null) {
                if (scrollDirection == FORWARD) {
                    currentScreenAction.screenScrollingOut(
                            currentScreen.getView(), 1.0f - positionOffset, scrollDirection);
                } else {
                    currentScreenAction.screenScrollingIn(
                            currentScreen.getView(), 1.0f - positionOffset, scrollDirection);
                }
            }

            if (otherScreenAction != null) {
                if (scrollDirection == FORWARD) {
                    otherScreenAction.screenScrollingIn(
                            otherScreen.getView(), positionOffset, scrollDirection);
                } else {
                    otherScreenAction.screenScrollingOut(
                            otherScreen.getView(), positionOffset, scrollDirection);
                }
            }
        }

        if (positionOffset != 0.0F) {
            lastPositionOffset = positionOffset;
        } else {
            lastPositionOffset = -1;
        }
    }

    public void onPageSelected(int position) {
        lastPositionOffset = -1;
        ScreenAction selectionPositionAction = screenList.get(position).getScreenAction();
        if (selectionPositionAction != null) {
            selectionPositionAction.screenSettled(screenList.get(position).getView());
        }
    }

    public void onPageScrollStateChanged(int state) {

    }
}
