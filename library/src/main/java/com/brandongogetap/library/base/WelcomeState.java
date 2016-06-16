package com.brandongogetap.library.base;

import android.view.View;

enum WelcomeState {

    FIRST_PAGE(View.VISIBLE, View.VISIBLE, View.GONE),
    MIDDLE(View.VISIBLE, View.VISIBLE, View.GONE),
    LAST_PAGE(View.GONE, View.GONE, View.VISIBLE);

    final int skipVisibility;
    final int nextVisibility;
    final int doneVisibility;

    WelcomeState(int skipVisibility, int nextVisibility, int doneVisibility) {
        this.skipVisibility = skipVisibility;
        this.nextVisibility = nextVisibility;
        this.doneVisibility = doneVisibility;
    }
}
