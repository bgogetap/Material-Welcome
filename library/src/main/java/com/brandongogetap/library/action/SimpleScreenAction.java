package com.brandongogetap.library.action;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.brandongogetap.library.action.ScreenActionListener.ScrollDirection;

/**
 * Extend this class when you don't care if a screen is scrolling in or scrolling out.
 *<p>
 * This class forwards calls to {@link ScreenAction#screenScrollingIn(View, float, int)} and
 * {@link ScreenAction#screenScrollingOut(View, float, int)}
 * to a single {@link SimpleScreenAction#screenScrolling(View, float, int)} method
 */
public abstract class SimpleScreenAction implements ScreenAction {

    @Override public void screenSettled(@NonNull View view) {

    }

    @Override public void screenCompletelyHidden(@Nullable View view) {

    }

    @Override
    public void screenScrollingOut(@NonNull View view, float scrollPosition, @ScrollDirection int direction) {
        screenScrolling(view, scrollPosition, direction);
    }

    @Override
    public void screenScrollingIn(@NonNull View view, float scrollPosition, @ScrollDirection int direction) {
        screenScrolling(view, scrollPosition, direction);
    }

    protected abstract void screenScrolling(@NonNull View view, float scrollPosition, @ScrollDirection int direction);
}
