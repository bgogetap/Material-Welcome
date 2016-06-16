package com.brandongogetap.library.base.action;

import android.support.annotation.NonNull;
import android.view.View;

import com.brandongogetap.library.base.action.ScreenActionListener.ScrollDirection;

/**
 * Convenience class to use when you do not need to override all {@link ScreenAction} methods.
 */
public class ScreenActionAdapter implements ScreenAction {

    @Override public void screenSettled(@NonNull View view) {
        // no-op
    }

    @Override public void screenCompletelyHidden(View view) {
        // no-op
    }

    @Override
    public void screenScrollingOut(@NonNull View view, float scrollPosition, @ScrollDirection int direction) {
        // no-op
    }

    @Override
    public void screenScrollingIn(@NonNull View view, float scrollPosition, @ScrollDirection int direction) {
        // no-op
    }
}
