package com.brandongogetap.library.action;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.brandongogetap.library.action.ScreenActionListener.ScrollDirection;

/**
 * This class allows you to receive callbacks to perform animations or other state changes on your
 * screens.
 *
 * The screen's view is passed in for convenience to all methods (to allow you to grab references
 * to child views for animation, etc.) however be sure to avoid holding strong references to the
 * view passed in or any other child views that you pull out from the parent. The views will be re-
 * created on config changes and any references you keep will leak if they don't get a chance to
 * update to reference the new view(s).
 *
 * If you do find holding references are necessary, at the very least, clear all references
 * in {@link ScreenAction#screenCompletelyHidden(View)}.
 * Using a WeakReference would suffice as well.
 */
public interface ScreenAction {

    /**
     * Called when the page (screen) is settled. This is now the currently "selected" screen
     *
     * @param view This screen's view passed for convenience
     */
    void screenSettled(@NonNull View view);

    /**
     * Called when a previously shown screen is no completely offscreen. Use this to reset any state
     * if necessary.
     *
     * @param view The now hidden screen's view, passed for convenience
     */
    void screenCompletelyHidden(@Nullable View view);

    /**
     * This method will be called as the current screen is being scrolled out in either direction to
     * reveal a new screen.
     *
     * If direction is FORWARD, the next screen in the list is being revealed.
     * If direction is BACKWARD, the previous screen in the list is being revealed.
     *
     * Note: You will not get calls with scrollPosition == 1 or scrollPosition == 0
     * Use {@link ScreenAction#screenSettled(View)} to handle finishing behavior
     *
     * @param view           The outgoing screen's view
     * @param scrollPosition Value from 0-1 indicating the amount of the screen left showing
     * @param direction The direction of the scroll
     */
    void screenScrollingOut(@NonNull View view, float scrollPosition, @ScrollDirection int direction);

    /**
     * This method will be called as a new screen is being revealed as another screen is being
     * scrolled out.
     *
     * If direction is FORWARD, this is the next screen in the list that's being revealed.
     * If direction is BACKWARD, this is the previous screen in the list that's being revealed.
     *
     * Note: You will not get calls with scrollPosition == 1 or scrollPosition == 0
     * Use {@link ScreenAction#screenSettled(View)} to handle finishing behavior
     *
     * @param view           The incoming screen's view
     * @param scrollPosition Value from 0-1 indicating the amount of the screen showing
     * @param direction The direction of the scroll
     */
    void screenScrollingIn(@NonNull View view, float scrollPosition, @ScrollDirection int direction);

}
