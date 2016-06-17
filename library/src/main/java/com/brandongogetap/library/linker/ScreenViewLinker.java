package com.brandongogetap.library.linker;

import android.view.View;

import com.brandongogetap.library.base.WelcomeScreen;

public class ScreenViewLinker {

    public static void linkListenersToViews(WelcomeScreen screen, View screenView) {
        for (int i = 0; i < screen.getViewIdClickListenerMap().size(); i++) {
            int id = screen.getViewIdClickListenerMap().keyAt(i);
            View view = screenView.findViewById(id);
            view.setOnClickListener(screen.getViewIdClickListenerMap().get(id));
        }
    }
}
