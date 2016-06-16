package com.brandongogetap.library.base;

interface WelcomeScreenContainer {

    void moveSelectedIndicator(int position);

    void updateBackgroundColor(int color);

    void updateWelcomeState(WelcomeState state);
}
