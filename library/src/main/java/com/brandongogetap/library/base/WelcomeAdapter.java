package com.brandongogetap.library.base;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brandongogetap.library.linker.ScreenViewLinker;

import java.util.List;

final class WelcomeAdapter extends PagerAdapter {

    private final List<WelcomeScreen> welcomeScreens;

    WelcomeAdapter(List<WelcomeScreen> welcomeScreens) {
        this.welcomeScreens = welcomeScreens;
    }

    @Override public Object instantiateItem(ViewGroup container, int position) {
        WelcomeScreen screen = welcomeScreens.get(position);
        View view = LayoutInflater.from(container.getContext())
                .inflate(screen.getLayoutResource(), container, false);
        container.addView(view);
        ScreenViewLinker.linkListenersToViews(screen, view);
        screen.setView(view);
        return view;
    }

    @Override public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override public int getCount() {
        return welcomeScreens.size();
    }

    @Override public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
