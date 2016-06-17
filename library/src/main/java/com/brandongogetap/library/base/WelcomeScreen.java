package com.brandongogetap.library.base;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;

import com.brandongogetap.library.action.ScreenAction;

import java.lang.ref.WeakReference;

/**
 * The building block of the Welcome Activity's pager.
 * <p>
 * Each screen represents one page of the Welcome Activity. A page can be composed of many views
 * and have actions bound to those views to dictate behavior.
 */
public class WelcomeScreen {

    private final int layoutResource;
    private final Integer color;
    private final Integer colorResource;
    private final ScreenAction screenAction;
    private final SparseArrayCompat<View.OnClickListener> viewIdClickListenerMap;

    private WeakReference<View> view;

    WelcomeScreen(
            @LayoutRes int layoutResource,
            @ColorInt Integer color,
            @ColorRes Integer colorResource,
            ScreenAction screenAction,
            SparseArrayCompat<View.OnClickListener> viewIdClickListenerMap
    ) {
        this.layoutResource = layoutResource;
        this.color = color;
        this.colorResource = colorResource;
        this.screenAction = screenAction;
        this.viewIdClickListenerMap = viewIdClickListenerMap;
    }

    @ColorInt int getBackgroundColor(Context context) {
        if (color != null) {
            return color;
        }
        if (colorResource != null) {
            return ContextCompat.getColor(context, colorResource);
        }
        return Color.TRANSPARENT;
    }

    int getLayoutResource() {
        return layoutResource;
    }

    public ScreenAction getScreenAction() {
        return screenAction;
    }

    public SparseArrayCompat<View.OnClickListener> getViewIdClickListenerMap() {
        return viewIdClickListenerMap;
    }

    @Nullable public View getView() {
        return view != null ? view.get() : null;
    }

    void setView(View view) {
        this.view = new WeakReference<>(view);
    }

    public static class Builder {

        @LayoutRes private int layoutResource;
        @ColorRes private Integer colorResource;
        @ColorInt private Integer color;
        private SparseArrayCompat<View.OnClickListener> idListenerMap;
        private ScreenAction screenAction;

        /**
         * Return a new instance of {@link WelcomeScreen.Builder} to specify what layout and
         * behaviors will be used for a particular page in the Welcome Activity.
         * <p>
         * Provide the layout resource that will be inflated to provide the view for this screen.
         * The parent ViewGroup's width/height will be set to MATCH_PARENT regardless of what they
         * are set to in XML.
         *
         * @param layoutResource The layout resource to be inflated
         */
        public Builder(@LayoutRes int layoutResource) {
            this.layoutResource = layoutResource;
            idListenerMap = new SparseArrayCompat<>();
        }

        /**
         * Background color for a this screen. If color was set using {@link #backgroundColor(int)},
         * the resource provided here will be ignored.
         * <p>
         * This color will be blended into the next
         * screen's color if scrolling forward, or the previous screen's color if scrolling
         * backward.
         *
         * @param colorResource Color resource to be used for the background color
         * @return Builder to allow chaining
         */
        public Builder backgroundColorResource(@ColorRes int colorResource) {
            this.colorResource = colorResource;
            return this;
        }

        /**
         * Background color for this screen. If color was set using
         * {@link #backgroundColorResource(int)}, it will be overridden by the color provided here.
         * <p>
         * This color will be blended into the next
         * screen's color if scrolling forward, or the previous screen's color if scrolling
         * backward.
         *
         * @param color Color int
         * @return Builder to allow chaining
         */
        public Builder backgroundColor(@ColorInt int color) {
            this.color = color;
            return this;
        }

        /**
         * Provide a {@link ScreenAction} that allows you to customize how your view will behave
         * while it's screen is being scrolled.
         *
         * @param screenAction A ScreenAction subclass
         * @return Builder to allow chaining
         */
        public Builder screenAction(ScreenAction screenAction) {
            this.screenAction = screenAction;
            return this;
        }

        /**
         * Add a {@link android.view.View.OnClickListener} to a view with the supplied ID.
         * <p>
         * This method can be used multiple times for a given screen.
         *
         * @param viewId   ID of the view that the listener will be registered to
         * @param listener Listener to be called when the view represented by viewId is clicked
         * @return Builder to allow chaining
         */
        public Builder onViewClicked(@IdRes int viewId, View.OnClickListener listener) {
            idListenerMap.append(viewId, listener);
            return this;
        }

        public WelcomeScreen build() {
            return new WelcomeScreen(
                    layoutResource, color, colorResource, screenAction, idListenerMap);
        }
    }
}
